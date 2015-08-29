#ifndef LINEAR_PROBING_HASH_TABLE_HPP
#define LINEAR_PROBING_HASH_TABLE_HPP

#include <functional>
#include <stdexcept>

template<typename Key, typename Value>
class LinearProbingHashTable
{
public:
	
	using hash_function_t = std::function<std::size_t(const Key& key)>;
	
	
	LinearProbingHashTable(const hash_function_t& function = std::hash<Key>())
	: _pairs(new Pair*[MINIMUM_CAPACITY])
	, _size(0)
	, _alive(0)
	, _capacity(MINIMUM_CAPACITY)
	, _hash_function(function)
	{
		for (std::size_t i = 0; i < _capacity; ++i)
		{
			_pairs[i] = nullptr;
		}
	}
	
	~LinearProbingHashTable()
	{
		for (std::size_t i = 0; i < _capacity; ++i)
		{
			delete _pairs[i];
		}
		
		delete [] _pairs;
	}
	
	
	void insert(const Key& key, const Value& value)
	{
		std::size_t hash = _hash(key);
		
		for ( ; _pairs[hash]; hash = ++hash % _capacity)
		{
			if (_pairs[hash]->key == key)
			{
				if (_pairs[hash]->is_dead)
				{
					++_alive;
					
					_pairs[hash]->is_dead = false;
				}
				
				_pairs[hash]->value = value;
				
				return;
			}
		}
		
		_pairs[hash] = new Pair(key, value);
		
		++_alive;
		
		if (++_size == _capacity) _resize();
	}
	
	void erase(const Key& key)
	{
		std::size_t hash = _hash(key);
		
		for ( ; _pairs[hash]; hash = ++hash % _capacity)
		{
			if (! _pairs[hash]->is_dead && _pairs[hash]->key == key)
			{
				_pairs[hash]->is_dead = true;
				
				if (--_alive == _capacity/4) _resize();
				
				return;
			}
		}

		throw std::invalid_argument("No such key!");
	}
	
	
	Value& at(const Key& key)
	{
		std::size_t hash = _hash(key);
		
		for ( ; _pairs[hash]; hash = ++hash % _capacity)
		{
			if (! _pairs[hash]->is_dead && _pairs[hash]->key == key)
			{
				return _pairs[hash]->value;
			}
		}
		
		throw std::invalid_argument("No such key!");
	}
	
	const Value& at(const Key& key) const
	{
		std::size_t hash = _hash(key);
		
		for ( ; _pairs[hash]; hash = ++hash % _capacity)
		{
			if (! _pairs[hash]->is_dead && _pairs[hash]->key == key)
			{
				return _pairs[hash]->value;
			}
		}
		
		throw std::invalid_argument("No such key!");
	}
	
	
	Value& operator[](const Key& key)
	{
		std::size_t hash = _hash(key);
		
		for ( ; _pairs[hash]; hash = ++hash % _capacity)
		{
			if (_pairs[hash]->key == key)
			{
				if (_pairs[hash]->is_dead)
				{
					++_alive;
					
					_pairs[hash]->is_dead = false;
				}
				
				return _pairs[hash]->value;
			}
		}
		
		_pairs[hash] = new Pair(key);
		
		++_alive;
		
		// After re-sizing and re-hashing
		// the hash index may no longer match
		Value& value = _pairs[hash]->value;
		
		if (++_size == _capacity) _resize();
		
		return value;
	}
	
	
	bool contains(const Key& key) const
	{
		std::size_t hash = _hash(key);
		
		for ( ; _pairs[hash]; hash = ++hash % _capacity)
		{
			if (! _pairs[hash]->is_dead && _pairs[hash]->key == key)
			{
				return true;
			}
		}
		
		return false;
	}
	
	void clear()
	{
		for (std::size_t i = 0; i < _capacity; ++i)
		{
			delete _pairs[i];
		}
		
		delete [] _pairs;
		
		_pairs = new Pair*[MINIMUM_CAPACITY];
	}
	
	std::size_t size() const
	{
		return _alive;
	}
	
	bool is_empty() const
	{
		return _alive == 0;
	}
	
private:
	
	static const std::size_t MINIMUM_CAPACITY = 4;
	
	struct Pair
	{
		Pair(const Key& k, const Value& v = Value())
		: key(k)
		, value(v)
		, is_dead(false)
		{ }
		
		Key key;
		Value value;
		
		bool is_dead;
	};
	
	std::size_t _hash(const Key& key)
	{
		return _hash_function(key) % _capacity;
	}
	
	void _resize()
	{
		std::size_t new_capacity = 2 * _alive;
		
		if (new_capacity < MINIMUM_CAPACITY) return;
		
		Pair** old = _pairs;
		
		_pairs = new Pair*[new_capacity];
		
		for (std::size_t i = 0; i < new_capacity; ++i)
		{
			_pairs[i] = nullptr;
		}
		
		std::size_t old_capacity = _capacity;
		
		// So we can use the hash function
		_capacity = new_capacity;
		
		for (std::size_t i = 0; i < old_capacity; ++i)
		{
			if (old[i] && ! old[i]->is_dead)
			{
				std::size_t hash = _hash(old[i]->key);
				
				while (_pairs[hash]) hash = ++hash % _capacity;
				
				_pairs[hash] = old[i];
			}
		}
		
		delete [] old;
		
		_size = _alive;
	}
	

	Pair** _pairs;
	
	std::size_t _size;
	
	std::size_t _alive;
	
	std::size_t _capacity;
	
	const hash_function_t _hash_function;
};

#endif /* LINEAR_PROBING_HASH_TABLE_HPP */
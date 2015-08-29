#ifndef SEPARATE_CHAINING_HASH_TABLE_HPP
#define SEPARATE_CHAINING_HASH_TABLE_HPP

#include <stdexcept>
#include <functional>

template<typename Key, typename Value>
class SeparateChainingHashTable
{
public:
	
	using hash_function_t = std::function<std::size_t(const Key& key)>;
	
	SeparateChainingHashTable(const hash_function_t& function = std::hash<Key>())
	: _nodes(new Node*[MINIMUM_CAPACITY])
	, _hash_function(function)
	, _size(0)
	, _bins(MINIMUM_CAPACITY)
	, _threshold(MINIMUM_CAPACITY * BIN_SIZE)
	{
		for (std::size_t bin = 0; bin < _bins; ++bin)
		{
			_nodes[bin] = nullptr;
		}
	}
	
	~SeparateChainingHashTable()
	{
		for (std::size_t bin = 0; bin < _bins; ++bin)
		{
			for (Node* node = _nodes[bin]; node; )
			{
				Node* next = node->next;
				
				delete node;
				
				node = next;
			}
		}
		
		delete [] _nodes;
	}
	
	void insert(const Key& key, const Value& value)
	{
		std::size_t hash = _hash(key);
		
		for (Node* node = _nodes[hash]; node; node = node->next)
		{
			if (node->key == key)
			{
				node->value = value;
				
				return;
			}
		}
		
		_nodes[hash] = new Node(key, value, _nodes[hash]);
		
		if (++_size == _threshold) _resize();
	}
	
	void erase(const Key& key)
	{
		std::size_t hash = _hash(key);
		
		Node* node = _nodes[hash];
		Node* previous = nullptr;
		
		for ( ; node; previous = node, node = node->next)
		{
			if (node->key == key)
			{
				if (previous) previous->next = node->next;
				
				else _nodes[hash] = node->next;
				
				delete node;
				
				if (--_size == _threshold/4) _resize();
				
				return;
			}
		}
		
		throw std::invalid_argument("No such key!");
	}
	
	
	Value& at(const Key& key)
	{
		std::size_t hash = _hash(key);
		
		for (Node* node = _nodes[hash]; node; node = node->next)
		{
			if (node->key == key) return node->value;
		}
		
		throw std::invalid_argument("No such key!");
	}
	
	const Value& at(const Key& key) const
	{
		std::size_t hash = _hash(key);
		
		for (Node* node = _nodes[hash]; node; node = node->next)
		{
			if (node->key == key) return node->value;
		}
		
		throw std::invalid_argument("No such key!");
	}

	
	bool contains(const Key& key) const
	{
		std::size_t hash = _hash(key);
		
		for (Node* node = _nodes[hash]; node; node = node->next)
		{
			if (node->key == key) return true;
		}
		
		return false;
	}
	
	
	Value& operator[](const Key& key)
	{
		std::size_t hash = _hash(key);
		
		for (Node* node = _nodes[hash]; node; node = node->next)
		{
			if (node->key == key) return node->value;
		}
		
		_nodes[hash] = new Node(key, { }, _nodes[hash]);
		
		if (++_size == _threshold) _resize();
		
		return _nodes[hash]->value;
	}
	
	std::size_t size() const
	{
		return _size;
	}
	
	bool is_empty() const
	{
		return _size == 0;
	}

private:
	
	static const std::size_t MINIMUM_CAPACITY = 4;
	
	static const std::size_t BIN_SIZE = 5;
	
	struct Node
	{
		Node(const Key& k, const Value& v, Node* n)
		: key(k)
		, value(v)
		, next(n)
		{ }
		
		Key key;
		Value value;
		
		Node* next;
	};
	
	std::size_t _hash(const Key& key)
	{
		return _hash_function(key) % _bins;
	}
	
	void _resize()
	{
		std::size_t new_bins = (_size / BIN_SIZE) * 2;
		
		if (new_bins < MINIMUM_CAPACITY) return;
		
		Node** old = _nodes;
		
		_nodes = new Node*[new_bins];
		
		for (std::size_t bin = 0; bin < new_bins; ++bin)
		{
			_nodes[bin] = bin < _bins ? old[bin] : nullptr;
		}
		
		delete [] old;
		
		_bins = new_bins;
		
		_threshold = _bins * BIN_SIZE;
		
		_rehash();
	}
	
	void _rehash()
	{
		for (std::size_t bin = 0; bin < _bins; ++bin)
		{
			Node* previous = nullptr;
			
			for (Node* node = _nodes[bin]; node; )
			{
				std::size_t hash = _hash(node->key);
				
				Node* next = node->next;
				
				node->next = _nodes[hash];
				
				_nodes[hash] = node;
				
				if (previous) previous->next = next;
				
				else if (bin != hash) _nodes[bin] = next;
				
				previous = node;
				
				node = next;
			}
		}
	}
	
	Node** _nodes;
	
	std::size_t _size;
	
	std::size_t _bins;
	
	std::size_t _threshold;
	
	const hash_function_t _hash_function;
	
};

#endif /* SEPARATE_CHAINING_HASH_TABLE_HPP */
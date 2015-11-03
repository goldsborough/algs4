#ifndef TRIE_HPP
#define TRIE_HPP

#include <algorithm>
#include <array>
#include <stdexcept>
#include <string>

template<
		 typename Value,
		 typename String = std::string,
		 std::size_t R = 128
        >
class Trie
{
public:
	
	using size_t = std::size_t;
	
	
	Trie()
	: _root(nullptr)
	, _size(0)
	{ }
	
	Trie(const std::initializer_list<std::pair<String, Value>>& list)
	{
		for (const auto& item : list)
		{
			insert(item.first, item.second);
		}
	}
	
	Trie(const Trie& other)
	: _size(other._size)
	{
		_root = _copy(other);
	}
	
	Trie(Trie&& other) noexcept
	: Trie()
	{
		swap(other);
	}
	
	Trie& operator=(Trie other)
	{
		swap(other);
		
		return *this;
	}
	
	void swap(Trie& other) noexcept
	{
		using std::swap;
		
		swap(_root, other._root);
		
		swap(_size, other._size);
	}
	
	friend void swap(Trie& first, Trie& second) noexcept
	{
		first.swap(second);
	}
	
	~Trie()
	{
		_clear(_root);
	}
	
	
	void insert(const String& key, const Value& value)
	{
		_root = _insert(_root, key, value);
	}
	
	
	const Value& get(const String& key) const
	{
		auto node = _find(_root, key);
		
		if (! node)
		{
			throw std::invalid_argument("No such key!");
		}
		
		return node->value;
	}
	
	Value& get(const String& key)
	{
		auto node = _find(_root, key);
		
		if (! node)
		{
			throw std::invalid_argument("No such key!");
		}
		
		return node->value();
	}
	
	
	void erase(const String& key)
	{
		_erase(_root, key);
	}
	
	void clear()
	{
		_clear(_root);
		
		_size = 0;
	}
	
	
	Value& operator[](const String& key)
	{
		auto node = _find(_root, key);
		
		if (node) return node->value();
		
		else node = new Node;
		
		_root = _insert(_root, key, node);
		
		return node->value();
	}
	
	
	bool contains(const String& key) const
	{
		return _find(_root, key) != nullptr;
	}
	
	
	size_t size() const
	{
		return _size;
	}
	
	bool is_empty() const
	{
		return _size == 0;
	}
	
private:
	
	class Node
	{
	public:
		
		Node(const Value& value = Value())
		: _value(value)
		, has_value(true)
		{
			std::fill(next.begin(), next.end(), nullptr);
		}
		
		Value& value()
		{
			return _value;
		}
		
		void value(const Value& new_value)
		{
			_value = new_value;
			
			has_value = true;
		}
		
		bool has_value;
		
		std::array<Node*, R> next;
		
	private:
		
		Value _value;
	};
	
	Node* _insert(Node* node,
				  const String& key,
				  const Value& value,
				  size_t index = 0)
	{
		bool was_null = node == nullptr;
		
		if (was_null) node = new Node;
		
		if (index == key.size())
		{
			node->value(value);
			
			if (was_null) ++_size;
		}
		
		else
		{
			auto& next = node->next[key[index]];
			
			next = _insert(next, key, value, ++index);
		}
		
		return node;
	}
	
	Node* _insert(Node* node,
				  const String& key,
				  Node* new_node,
				  size_t index = 0)
	{
		if (! node) node = new Node;
		
		if (index == key.size()) node = new_node;
		
		else
		{
			auto& next = node->next[key[index]];
			
			next = _insert(next, key, new_node, ++index);
		}
		
		return node;
	}
	
	Node* _erase(Node* node, const String& key, size_t index = 0)
	{
		if (! node)
		{
			throw std::invalid_argument("No such key!");
		}
		
		if (index == key.size())
		{
			if (std::none_of(node->next.begin(),
							 node->next.begin(),
							 [&] (const Node* node) {
								 return node != nullptr;
							 }))
			{
				delete node;
				
				--_size;
				
				return nullptr;
			}
			
			else node->has_value = false;
		}
		
		auto& next = node->next[key[index]];
		
		next = _erase(next, key, ++index);
		
		return node;
	}
	
	Node* _find(Node* node, const String& key, size_t index = 0) const
	{
		if (! node) return nullptr;
		
		if (index == key.size()) return node;
		
		auto next = node->next[key[index]];
		
		return _find(next, key, ++index);
	}
	
	void _clear(Node* node)
	{
		if (! node) return;
		
		for (auto& next : node->next) _clear(next);
		
		delete node;
	}
	
	Node* _copy(const Node* other)
	{
		if (! other) return nullptr;
		
		auto node = new Node(other->value);
		
		for (size_t i = 0; i < R; ++i)
		{
			node->next[i] = _copy(node->next[i], other->next[i]);
		}
		
		return node;
	}
	
	Node* _root;
	
	size_t _size;
};

#endif /* TRIE_HPP */

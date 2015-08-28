#ifndef BINARY_SEARCH_TREE_HPP
#define BINARY_SEARCH_TREE_HPP

#include <stdexcept>

template<typename Key, typename Value>
class BinarySearchTree
{
public:
	
	typedef unsigned long size_t;
	
	struct Pair { const Key& key; Value& value; };
	
private:
	
	
	struct Node
	{
		Node(const Key& k = Key(), const Value& v = Value())
		: key(k)
		, value(v)
		, pair{key, value}
		, parent(nullptr)
		, left(nullptr)
		, right(nullptr)
		, size(1)
		{ }
		
		void resize()
		{
			size = 1;
			
			if (left) size += left->size;
			
			if (right) size += right->size;
		}
		
		Key key;
		Value value;
		Pair pair;
		
		Node* left;
		Node* right;
		Node* parent;
		
		size_t size;
	};
	
	void _insert(Node*& node, const Key& key, const Value& value)
	{
		if (! node) node = new Node(key, value);
		
		else if (node == _end)
		{
			node = new Node(key, value);
			
			node->right = _end;
			_end->parent = node;
		}
		
		else if (key < node->key)
		{
			_insert(node->left, key, value);
			
			node->left->parent = node;
			
			if (node == _begin) _begin = node->left;
			
			node->resize();
		}
		
		else if (key > node->key)
		{
			_insert(node->right, key, value);
			
			node->right->parent = node;
			
			node->resize();
		}
	}
	
	Node* _insert(Node*& node, Node* parent, const Key& key)
	{
		if (node)
		{
			if (node == _end)
			{
				node = new Node(key);
				
				node->right = _end;
				node->parent = parent;
				_end->parent = node;
				
			}
			
			if (key < node->key)
			{
				Node* child = _insert(node->left, node, key);
				
				if (node == _begin) _begin = child;
				
				node->resize();
				
				return child;
			}
			
			else if (key > node->key)
			{
				Node* child = _insert(node->left, node, key);
				
				node->resize();
				
				return child;
			}
		}
		
		else
		{
			node = new Node(key);
			node->parent = parent;
		}
		
		return node;
	}
	
	Node* _erase(Node* node, const Key& key)
	{
		if (node)
		{
			if (key < node->key)
			{
				node->left = _erase(node->left, key);
				node->resize();
				
			}
			
			else if (key > node->key)
			{
				node->right = _erase(node->right, key);
				node->resize();
			}
			
			else return _erase(node);
		}
		
		return node;
	}
	
	Node* _erase(Node* node)
	{
		if (node == _begin)
		{
			if (node->right) _begin = node->right;
			
			else _begin = _begin->parent;
		}
		
		if (! node->left) return node->right;
		
		if (! node->right || node->right == _end) return node->left;
		
		Node* successor = node->right;
		
		while(successor->left)
		{
			successor->size--;
			successor = successor->left;
		}
		
		successor->parent->left = successor->right;
		
		successor->right = node->right;
		successor->left = node->left;
		
		node->right->parent = successor;
		node->left->parent = successor;
		
		delete node;
		
		return successor;
	}
	
	Node*& _find(Node*& node, const Key& key) const
	{
		if (! node || node == _end || key == node->key) return node;
		
		else if (key < node->key) return _find(node->left, key);
		
		else return _find(node->right, key);
	}
	
	Node* _smallest() const
	{
		if (! _root) return _end;
		
		Node* node = _root;
		
		while (node->left) node = node->left;
		
		return node;
	}
	
	Node* _largest() const
	{
		if (! _root) return _end;
		
		Node* node = _root;
		
		while (node->right) node = node->right;
		
		return node;
	}
	
	Node* _floor(Node* node, const Key& key) const
	{
		if (! node || node == _end) return nullptr;
		
		else if (key > node->key)
		{
			Node* result = _floor(node->right, key);
			
			return result ? result : node;
		}
		
		else return _floor(node->left, key);
	}
	
	Node* _ceiling(Node* node, const Key& key) const
	{
		if (! node || node == _end) return nullptr;
		
		else if (key < node->key)
		{
			Node* result = _ceiling(node->left, key);
			
			return result ? result : node;
		}
		
		else return _ceiling(node->right, key);
	}
	
	size_t _rank(Node* node, const Key& key) const
	{
		if (! node || node == _end) return 0;
		
		if (key < node->key) return _rank(node->left, key);
		
		size_t size = 1;
		
		if (node->left) size += node->left->size;
		
		if (key > node->key) size += _rank(node->right, key);
		
		return size;
	}
	
	Node* _select(Node* node, size_t rank) const
	{
		if (! node || node == _end) return nullptr;
		
		size_t size = node->left ? node->left->size : 0;
		
		if (size > rank) return _select(node->left, rank);
		
		else if (size < rank) return _select(node->right, rank - size - 1);
		
		else return node;
	}
	
	void _clear(Node* node)
	{
		if (node)
		{
			if (node->left) _clear(node->left);
			
			if (node->right) _clear(node->right);
			
			delete node;
		}
	}
	
	Node* _begin;
	
	Node* _end;
	
	Node* _root;
	
public:
	
	class Iterator
	{
	public:
		
		Iterator(Node* node, const BinarySearchTree* bst)
		: _node(node)
		, _bst(bst)
		{ }
		
		Pair& operator*() const
		{
			return _node->pair;
		}
		
		Pair* operator->() const
		{
			return &_node->pair;
		}
		
		bool operator==(Iterator other) const
		{
			return _node == other._node;
		}
		
		bool operator!=(Iterator other) const
		{
			return _node != other._node;
		}
		
		bool operator<(Iterator other) const
		{
			if (! _node) return false;
			
			if (! other._node) return true;
			
			return _node->key < other._node->key;
		}
		
		bool operator<=(Iterator other) const
		{
			if (! _node) return false;
			
			if (! other._node) return true;
			
			return _node->key <= other._node->key;
		}
		
		bool operator>(Iterator other) const
		{
			if (! other._node) return false;
			
			if (! _node) return true;
			
			return _node->key > other._node->key;
		}
		
		bool operator>=(Iterator other) const
		{
			if (! other._node) return false;
			
			if (! _node) return true;
			
			return _node->key >= other._node->key;
		}
	
		Iterator& operator++()
		{
			if (_node != _bst->_end)
			{
				if (_node->right)
				{
					_node = _node->right;
					
					while (_node->left) _node = _node->left;
				}
				
				else _node = _node->parent;
			}
			
			return *this;
		}
		
		Iterator operator++(int)
		{
			Iterator previous = *this;
			
			++(*this);
			
			return previous;
		}
	
		Iterator& operator--()
		{
			if (_node != _bst->_begin)
			{
				if (_node->left)
				{
					_node = _node->left;
					
					while (_node->right) _node = _node->right;
				}
				
				else _node = _node->parent;
			}
			
			return *this;
		}
		
		Iterator operator--(int)
		{
			Iterator previous = *this;
			
			--(*this);
			
			return previous;
		}
		
		
	private:
		
		friend class BinarySearchTree;
		
		Node* _node;
		
		const BinarySearchTree* _bst;
		
	};
	
	BinarySearchTree()
	: _begin(nullptr)
	, _end(new Node)
	, _root(_end)
	{ }
	
	~BinarySearchTree()
	{
		clear();
	}
	
	
	Iterator begin() const
	{
		return {_begin, &*this};
	}
	
	Iterator end() const
	{
		return {_end, &*this};
	}
	
	Value& operator[](const Key& key)
	{
		Node* node = _insert(_root, nullptr, key);
		
		if (! _begin) _begin = node;
		
		return node->value;
	}
	
	void insert(const Key& key, const Value& value)
	{
		_insert(_root, key, value);
		
		if (! _begin) _begin = _root;
	}
	
	
	Value& at(const Key& key)
	{
		Node* node = _find(_root, key);
		
		if (! node) throw std::invalid_argument("Invalid key!");
		
		return node->value;
	}
	
	const Value& at(const Key& key) const
	{
		Node* node = _find(_root, key);
		
		if (! node) throw std::invalid_argument("Invalid key!");
		
		return node->value;
	}
	
	void clear()
	{
		_clear(_root);
		
		_root = nullptr;
	}
	
	void erase(const Key& key)
	{
		_erase(_root, key);
	}
	
	Iterator erase(Iterator itr)
	{
		Node* node = (itr++).node;
		
		Node* successor;
		
		if (! node->right) successor = node->left;
		
		else if (! node->left) successor = node->right;
		
		else
		{
			successor = node->right;
		
			while(successor->left)
			{
				successor->size--;
				successor = successor->left;
			}
			
			successor->parent->left = successor->right;
			
			successor->right = node->right;
			successor->left = node->left;
		}
		
		if (node == node->parent->right)
		{
			node->parent->right = successor;
		}
		
		else node->parent->left = successor;
		
		delete node;
		
		return itr;
	}
	
	
	bool contains(const Key& key) const
	{
		return _find(_root, key);
	}
	
	Iterator find(const Key& key) const
	{
		Node* node = _find(_root, key);
		
		return {node, *this};
	}
	
	size_t size() const
	{
		return _root ? _root->size : 0;
	}
	
	size_t size(const Key& lower, const Key& upper) const
	{
		size_t first = rank(lower);
		size_t second = rank(upper);
		
		return second - first + 1;
	}
	
	bool is_empty() const
	{
		return size() == 0;
	}
	
	
	Iterator smallest() const
	{
		return begin();
	}
	
	Iterator largest() const
	{
		return --end();
	}
	
	
	Iterator floor(const Key& key) const
	{
		return _floor(_root, key);
	}
	
	Iterator ceiling(const Key& key) const
	{
		return _ceiling(_root, key);
	}
	
	
	size_t rank(const Key& key) const
	{
		return _rank(_root, key);
	}
	
	Iterator select(size_t rank) const
	{
		return _select(_root, rank);
	}
	
};

#endif /* BINARY_SEARCH_TREE_HPP */
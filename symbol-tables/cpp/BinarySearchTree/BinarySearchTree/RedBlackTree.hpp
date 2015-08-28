#ifndef RED_BLACK_TREE_HPP
#define RED_BLACK_TREE_HPP

#include <assert.h>
#include <stdexcept>

template<typename Key, typename Value>
class RedBlackTree
{
public:
	
	typedef unsigned long size_t;
	
	RedBlackTree()
	: _root(nullptr)
	{ }
	
	~RedBlackTree()
	{
		_clear(_root);
	}
	
	
	void insert(const Key& key, const Value& value)
	{
		_root = _insert(_root, key, value);
	}
	
	void erase(const Key& key)
	{
		_root = _erase(_root, key);
	}
	
	void clear()
	{
		_clear();
	}
	
	
	Value& at(const Key& key)
	{
		Node* node = _find(_root, key);
		
		if (! node) throw std::invalid_argument("Key not found!");
		
		return node->value;
	}
	
	const Value& at(const Key& key) const
	{
		Node* node = _find(_root, key);
		
		if (! node) throw std::invalid_argument("Key not found!");
		
		return node->value;
	}
	
	bool contains(const Key& key) const
	{
		return _find(_root, key);
	}
	
	
	Value& operator[](const Key& key)
	{
		return _insert(_root, key);
	}
	
	
	size_t size() const
	{
		return _root ? _root->size : 0;
	}
	
	size_t size(const Key& lower, const Key& upper)
	{
		size_t first = rank(lower);
		size_t second = rank(upper);
		
		return second - first + 1;
	}
	
	bool is_empty() const
	{
		return size() == 0;
	}
	
	
	size_t rank(const Key& key) const
	{
		return _rank(_root, key);
	}
	
	const Key& select(size_t rank) const
	{
		Node* node = _select(_root, rank);
		
		if (! node) throw std::out_of_range("Rank out of range!");
		
		return node->key;
	}
	
	
	const Key& smallest() const
	{
		Node* node = _root;
		
		if (! node) throw std::runtime_error("No smallest element!");
		
		while (node->left) node = node->left;
		
		return node->key;
	}
	
	const Key& largest() const
	{
		Node* node = _root;
		
		if (! node) throw std::runtime_error("No largest element!");
		
		while (node->right) node = node->right;
		
		return node->key;
	}
	
	
	const Key& floor() const
	{
		Node* node = _floor(_root);
		
		if (! node) throw std::runtime_error("Cannot find floor!");
		
		return node->key;
	}
	
	const Key& ceiling() const
	{
		Node* node = _ceiling(_root);
		
		if (! node) throw std::runtime_error("Cannot find ceiling!");
		
		return node->key;
	}
	
private:
	
	enum class Color
	{
		RED,
		BLACK
	};
	
	struct Node
	{
		Node(const Key& k, const Value& v = Value())
		: key(k)
		, value(v)
		, color(Color::RED)
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
		
		Color color;
		
		Node* left;
		Node* right;
		
		size_t size;
	};
	
	void _clear(Node* node)
	{
		if (! node) return;
		
		_clear(node->left);
		
		_clear(node->right);
		
		delete node;
	}
	
	Node* _insert(Node* node, const Key& key, const Value& value)
	{
		if (! node) return new Node(key, value);
		
		else if (key < node->key)
		{
			node->left = _insert(node->left, key, value);
			
			node->resize();
		}
		
		else if (key > node->key)
		{
			node->right = _insert(node->right, key, value);
			
			node->resize();
		}
		
		else node->value = value;
		
		return _handle_colors(node);
	}
	
	Value& _insert(Node*& node, const Key& key)
	{
		if (! node) node = new Node(key);
		
		else if (key < node->key)
		{
			Value& value = _insert(node->left, key);
			
			node->resize();
			
			return value;
		}
		
		else if (key > node->key)
		{
			Value& value = _insert(node->right, key);
			
			node->resize();
			
			return value;
		}
		
		return node->value;
	}
	
	Node* _erase(Node* node, const Key& key)
	{
		if (! node) throw std::invalid_argument("No such key!");
		
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
		
		else
		{
			Node* successor = node->right;
			
			if (node->right)
			{
				Node* parent = nullptr;
				
				while (successor->left)
				{
					parent = successor;
					parent->size--;
					successor = successor->left;
				}
				
				successor->left = node->left;
				
				if (parent)
				{
					parent->left = successor->right;
					successor->right = node->right;
				}
			}
			
			else successor = node->left;
			
			delete node;
			
			return successor;
		}
		
		return node;
	}
	
	Node* _find(Node* node, const Key& key) const
	{
		if (! node) return nullptr;
		
		else if (key < node->key) return _find(node->left, key);
		
		else if (key > node->key) return _find(node->right, key);
		
		else return node;
	}
	
	size_t _rank(Node* node, const Key& key) const
	{
		if (! node) return 0;
		
		if (key < node->key) return _rank(node->left, key);
		
		size_t size = 1;
		
		if (node->left) size += node->left->size;
		
		if (key > node->key) size += _rank(node->right, key);
		
		return size;
	}
	
	Node* _select(Node* node, size_t rank) const
	{
		if (! node) return nullptr;
		
		size_t size = node->left ? node->left->size : 0;
		
		if (rank < size) return _select(node->left, rank);
		
		else if (rank > size) return _select(node->right, rank - size - 1);
		
		else return node;
	}
	
	Node* _floor(Node* node, const Key& key) const
	{
		if (! node) return nullptr;
		
		if (key > node->key)
		{
			Node* result = _floor(node->right, key);
			
			return result ? result : node;
		}
		
		else return _floor(node->left, key);
	}
	
	Node* _ceiling(Node* node, const Key& key) const
	{
		if (! node) return nullptr;
		
		if (key < node->key)
		{
			Node* result = _floor(node->left, key);
			
			return result ? result : node;
		}
		
		else return _floor(node->right, key);
	}
	
	bool _red(Node* node) const
	{
		if (! node || node == _root) return false;
		
		return node->color == Color::RED;
	}
	
	Node* _rotate_left(Node* node)
	{
		Node* right = node->right;
		
		assert(_red(right));
		
		node->right = right->left;
		
		right->left = node;
		
		right->color = node->color;
		
		node->color = Color::RED;
		
		return right;
	}
	
	Node* _rotate_right(Node* node)
	{
		Node* left = node->left;
		
		assert(_red(left));
		
		node->left = left->right;
		
		left->right = node;
		
		left->color = node->color;
		
		node->color = Color::RED;
		
		return left;
	}
	
	void _flip_colors(Node* node)
	{
		assert(! _red(node));
		
		assert(_red(node->left));
		
		assert(_red(node->right));
		
		node->color = Color::RED;
		
		node->left->color =  Color::BLACK;
		
		node->right->color = Color::BLACK;
	}
	
	Node* _handle_colors(Node* node)
	{
		if (_red(node->right))
		{
			node = _rotate_left(node);
			
			node->resize();
			node->left->resize();
		}
		
		if (_red(node->left) && _red(node->left->left))
		{
			node = _rotate_right(node);
			
			node->resize();
			node->right->resize();
		}
		
		if (_red(node->left) && _red(node->right))
		{
			_flip_colors(node);
		}
		
		return node;
	}
	
	
	Node* _root;
};

#endif /* RED_BLACK_TREE_HPP */
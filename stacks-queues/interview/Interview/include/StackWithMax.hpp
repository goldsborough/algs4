#ifndef STACK_WITH_MAX_HPP
#define STACK_WITH_MAX_HPP

/*
Stack with max.
 
Create a data structure that efficiently supports the stack operations
(push and pop) and also a return-the-maximum operation. Assume the
elements are reals numbers so that you can compare them.
*/

template<typename T>
class StackWithMax1
{
public:
	
	StackWithMax()
	: _root(nullptr)
	, _last(nullptr)
	, _max(nullptr)
	, _size(0)
	{ }
	
	~StackWithMax()
	{
		_clear();
	}
	
	void insert(const T& value)
	{
		_root = _insert(nullptr, _root, value);
		
		++_size;
	}
	
	void remove(const T& value)
	{
		Node* node = _find(_root, value);
		
		if (! node) return;
		
		Node* new_node;
		
		if (node->greater)
		{
			new_node = node->greater;
			
			Node* less_root = new_node;
			
			while(less_root->less) less_root = node->less;
			
			less_root->less = node->less;
		}
		
		else new_node = node->less;
		
		if (value < node->parent->value)
		{
			node->parent->less = new_node;
		}
		
		else node->parent->greater = new_node;
		
		if (node == _max) _max = node->parent;
		
		if (node == _last) _last = node->previous;
		
		delete node;
		
		--_size;
	}
	
	void remove_last()
	{
		remove(_last);
	}
	
	bool has(const T& value)
	{
		return _find(_root, value) != nullptr;
	}
	
	T max()
	{
		return _max->value;
	}
	
	int size()
	{
		return _size;
	}
	
	bool empty()
	{
		return _size == 0;
	}
	
private:
	
	struct Node
	{
		Node(const T& item, Node* root, Node* prev)
		: value(item)
		, parent(root)
		, previous(prev)
		, less(nullptr)
		, greater(nullptr)
		{ }
		
		T value;
		
		Node* parent;
		
		Node* previous;
		
		Node* less;
		Node* greater;
	};
	
	Node* _insert(Node* parent, Node* node, const T& value)
	{
		if (! node)
		{
			_last = new Node(value, parent, _last);
			
			if (! _max || value > _max->value) _max = _last;
			
			return new_node;
		}
		
		if (value < node->value)
		{
			node->less = _insert(node, node->less, value);
		}
		
		else if (value > node->value)
		{
			node->greater = _insert(node, node->greater, value);
		}
		
		return node;
	}
	
	Node* _find(Node* node, const T& value)
	{
		if (value < node->value) return _find(node->less, value);
		
		if (value > node->value) return _find(node->greater, value);
		
		return node;
	}
	
	void _clear(Node* node)
	{
		if (! node) return;
		
		_clear(node->less);
		
		_clear(node->right);
		
		delete node;
	}
	
	Node* _root;
	
	Node* _max;
	
	Node* _last;
	
	int _size;
};

template<typename T>
class Stack
{
public:
	
	Stack(int capacity = 4)
	: _size(0)
	, _capacity(capacity)
	, _data(new T[capacity])
	{ }
	
	~Stack()
	{
		delete [] _data;
	}
	
	void push(const T& item)
	{
		_data[_size] = item;
		
		if (++_size == _capacity) _resize();
	}
	
	T top()
	{
		return _data[size - 1];
	}
	
	T pop()
	{
		T item = _data[--_size];
		
		if (_size == _capacity/4) _resize();
		
		return item;
	}
	
	int size()
	{
		return _size;
	}
	
	bool empty()
	{
		return _size == 0;
	}
	
private:
	
	void _resize()
	{
		T* old = _data;
		
		_capacity = _size * 2;
		
		_data = new T[_capacity];
		
		for (int i = 0; i < _size; ++i)
		{
			_data[i] = old[i];
		}
		
		delete [] old;
	}
	
	T* _data;
	
	int _size;
	
	int _capacity;
};

template<typename T>
class StackWithMax2
{
public:
	
	void push(const T& value)
	{
		_values.push(value);
		
		if (value > _max.top()) _max.push(value);
	}
	
	T pop()
	{
		T value = _values.pop();
		
		if (value == _max.top()) _max.pop();
		
		return value;
	}
	
	T max()
	{
		return _max.top();
	}
	
	int size()
	{
		return _values.size();
	}
	
	bool empty()
	{
		return _values.empty();
	}
	
private:
	
	Stack<T> _values;
	
	Stack<T> _max;
	
};

#endif /* STACK_WITH_MAX_HPP */

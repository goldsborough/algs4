#ifndef LINKED_LIST_STACK_HPP
#define LINKED_LIST_STACK_HPP

template<typename T>
class LinkedListStack
{
public:
	
	LinkedListStack()
	: _first(nullptr)
	{ }
	
	~LinkedListStack()
	{
		while (_first != nullptr)
		{
			Node* node = _first;
			
			_first = _first->next;
			
			delete node;
		}
	}
	
	void push(const T& item)
	{
		Node* node = new Node(item, _first);
		
		_first = node;
	}
	
	T pop()
	{
		Node* node = _first;
		
		_first = _first->next;
		
		T item = node->item;
		
		delete node;
		
		return item;
	}
	
	bool isEmpty() const
	{
		return ! _first;
	}
	
	int size() const
	{
		int count = 0;
		
		Node* node = _first;
		
		while(node)
		{
			count++;
			node = node->next;
		}
		
		return count;
	}
	
private:
	
	struct Node
	{
		Node(const T& i, Node* node)
		: item(i)
		, next(node)
		{ }
		
		T item;
		Node* next;
	};
	
	Node* _first;
};

#endif /* LINKED_LIST_STACK_HPP */
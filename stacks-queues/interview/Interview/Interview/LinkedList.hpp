#ifndef LINKED_LIST_HPP
#define LINKED_LIST_HPP

/*
Detect cycle in a linked list.
 
A singly-linked data structure is a data
structure made up of nodes where each node has a pointer to the next node
(or a pointer to null). Suppose that you have a pointer to the first node of
a singly-linked list data structure: Determine whether a singly-linked data
structure contains a cycle. You may use only two pointers into the list (and
no other variables). The running time of your algorithm should be linear in
the number of nodes in the data structure.
 
If a singly-linked data structure contains a cycle, determine the first node
that participates in the cycle. you may use only a constant number of pointers
into the list (and no other variables). The running time of your algorithm
should be linear in the number of nodes in the data structure. 
*/

template<typename T>
class LinkedList
{
public:
	
	LinkedList()
	: _size(0)
	, _first(nullptr)
	{ }
	
	~LinkedList()
	{
		while(_first)
		{
			Node* node = _first;
			_first = _first->next;
			delete node;
		}
	}
	
	void push(const T& value)
	{
		Node* node = new Node(value, _first);
		
		_first = node;
		
		++_size;
	}
	
	T top()
	{
		return _first->value;
	}
	
	T pop()
	{
		T value = _first->value;
		
		Node* node = _first;
		
		_first = _first->next;
		
		delete node;
		
		--_size;
		
		return value;
	}
	
	int size()
	{
		return _size;
	}
	
	bool empty()
	{
		return _size == 0;
	}
	
	bool hasCycle()
	{
		Node* tortoise = _first;
		Node* hare = _first->next;
		
		while(hare)
		{
			if (tortoise == hare) return true;
			
			tortoise = tortoise->next;
			
			if (! hare->next) return false;
			
			hare = hare->next->next;
		}
		
		return false;
	}
	
private:
	
	struct Node
	{
		Node(const T& val, Node* node)
		: value(val)
		, next(node)
		{ }
		
		T value;
		
		Node* next;
	};
	
	Node* _first;
	
	int _size;
};

#endif /* LINKED_LIST_HPP */
#ifndef LINKED_LIST_WITH_RANDOM_LINK_HPP
#define LINKED_LIST_WITH_RANDOM_LINK_HPP

/*
 Clone a linked structure with two pointers per node.
 
 Suppose that you are given a reference to the first node of a linked structure
 where each node has two pointers: one pointer to the next node in the sequence
 (as in a standard singly-linked list) and one pointer to an arbitrary node.
 
 private class Node {
 private String item;
 private Node next;
 private Node random;
 }
 
 Design a linear-time algorithm to create a copy of the doubly-linked structure.
 You may modify the original linked structure, but you must end up with two
 copies of the original.
 */

template<typename T>
class LinkedList
{
public:
	
	LinkedList()
	: _size(0)
	, _first(nullptr)
	{ }
	
	LinkedList(const LinkedList& other)
	: LinkedList()
	{
		*this = other;
	}
	
	LinkedList& operator=(LinkedList& other)
	{
		if (this != &other)
		{
			Node* previous = nullptr;
			
			Node* first = other._first;
			
			while(first)
			{
				Node* node = new Node(*first);
				
				if (previous) previous->next = node;
				
				previous = node;
				
				node->next = first->next;
				
				first->next = node;

				first = node->next;
			}
			
			first = other._first;
			
			while(first)
			{
				Node* copy = first->next;
				
				copy->random = first->random->next;
				
				first->next = first->next->next;
			}
		}
		
		return *this;
	}
	
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
	
private:
	
	struct Node
	{
		Node(const T& val, Node* node)
		: value(val)
		, next(node)
		, random(nullptr)
		{ }
		
		T value;
		
		Node* next;
		Node* random;
	};
	
	Node* _first;
	
	int _size;
};

#endif /* LINKED_LIST_WITH_RANDOM_LINK_HPP */
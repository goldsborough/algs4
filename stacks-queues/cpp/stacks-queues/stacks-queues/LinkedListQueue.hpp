#ifndef LINKED_LIST_QUEUE_HPP
#define LINKED_LIST_QUEUE_HPP

template<typename T>
class LinkedListQueue
{
public:
	
	LinkedListQueue()
	: _size(0)
	, _first(nullptr)
	, _last(nullptr)
	{ }
	
	~LinkedListQueue()
	{
		while(_first)
		{
			Node* node = _first;
			
			_first = _first->next;
			
			delete node;
		}
	}
	
	void enqueue(const T& item)
	{
		Node* node = new Node(item);
		
		if (_last) _last->next = node;
		
		else _first = node;
		
		_last = node;
		
		++_size;
	}
	
	T dequeue()
	{
		Node* node = _first;
		
		_first = _first->next;
		
		T item = node->item;
		
		delete node;
		
		--_size;
		
		return item;
	}
	
	bool isEmpty() const
	{
		return _size == 0;
	}
	
	int size() const
	{
		return _size;
	}
	
private:
	
	struct Node
	{
		Node(const T& i)
		: next(nullptr)
		, item(i)
		{ }
		
		Node* next;
		T item;
	};
	
	Node* _first;
	
	Node* _last;
	
	int _size;
};

#endif /* LINKED_LIST_QUEUE_HPP */
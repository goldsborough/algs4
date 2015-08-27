#include "BinarySearchTree.hpp"

#include <string>
#include <iostream>

void print(const std::string& end)
{
	std::cout << end;
}

template<typename Head, typename... Tail>
void print(Head&& head, Tail&&... tail, const std::string& end = "\n")
{
	std::cout << std::forward<Head>(head);
	
	print(std::forward<Tail>(tail)..., end);
}

int main(int argc, char* argv[])
{
	BinarySearchTree<std::string, std::size_t> map;
	
	map["one"] = 123;
	map.insert("two", 2);
	map.insert("three", 3);
	map["one"] = 777;
	
	print(map.size());
	print(map.is_empty());
	print(map["one"]);
	print(map.at("two"));
	print(map.smallest()->key);
	print(map.largest()->value);
	print(map.rank("three"));
	print(map.size("one", "three"));
	
	
	for (auto itr = map.begin(), end = map.end(); itr != end; ++itr)
	{
		std::cout << itr->key << " : " << itr->value << "\n";
	}
}
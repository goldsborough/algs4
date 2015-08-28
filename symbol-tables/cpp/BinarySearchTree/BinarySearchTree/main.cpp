#include "BinarySearchTree.hpp"
#include "RedBlackTree.hpp"

#include <string>
#include <iostream>

void print()
{
	std::cout << "\n";
}

template<typename Head, typename... Tail>
void print(Head&& head, Tail&&... tail)
{
	std::cout << std::forward<Head>(head);
	
	print(std::forward<Tail>(tail)...);
}

int main(int argc, char* argv[])
{
	RedBlackTree<std::string, std::size_t> map;
	
	map["one"] = 123;
	map.insert("two", 2);
	map.insert("three", 3);
	map["seven"] = 7;
	
	print(map.size());
	print(std::boolalpha, map.is_empty());
	print(map["one"]);
	print(map.at("two"));
	print(map.contains("three"));
	print(map.contains("four"));
	print(map.smallest());
	print(map.largest());
	print(map.rank("three"));
	print(map.size("one", "three"));
	
	map.erase("one");
	
	print(map.size());
	print(map.contains("one"));
	print(map.smallest());
	
	while (! map.is_empty()) map.erase(map.smallest());
	
	print(map.is_empty());
	print(map.size());
}
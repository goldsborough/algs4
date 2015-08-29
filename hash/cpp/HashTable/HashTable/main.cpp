#include "SeparateChainingHashTable.hpp"
#include "LinearProbingHashTable.hpp"

#include <iostream>
#include <string>

void print()
{
	std::cout << std::endl;
}

template<typename Head, typename... Tail>
void print(Head&& head, Tail&&... tail)
{
	std::cout << std::forward<Head>(head);
	
	print(std::forward<Tail>(tail)...);
}

int main(int argc, const char* argv[])
{
	LinearProbingHashTable<std::string, std::size_t> table;
	
	table["one"] = 1;
	table["two"] = 2;
	table.insert("three", 3);
	table["four"] = 4;
	table["four"] = 4;
	table["five"] = 5;
	
	print(table.size());

	print(std::boolalpha, table.is_empty());
	print(table["two"]);

	table["one"] = 123;

	print(table["one"]);
	print(table["two"]);

	table.erase("three");
	print(table["two"]);
	table.erase("four");
	table.erase("five");
	
	print(table.size());
	print(table["two"]);
	
	table.erase("one");
	table.erase("two");
	
	print(table.is_empty());
}

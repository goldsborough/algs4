#include "Trie.hpp"

#include <iostream>

void println()
{
	std::cout << std::endl;
}

template<typename Head, typename... Tail>
void println(Head&& head, Tail&&... tail)
{
	std::cout << std::forward<Head>(head) << " ";
	
	println(std::forward<Tail>(tail)...);
}

int main(int argc, const char * argv[])
{
	Trie<int> trie = {{"she", 3}};
	
	trie.insert("sells", 5);
	
	trie.insert("sea", 7);
	
	trie.insert("shore", 2);
	
	trie.insert("by", 0);
	
	println(std::boolalpha);
	
	println(trie.size(), trie.is_empty());
	
	println(trie["shore"], trie.get("sea"));
}

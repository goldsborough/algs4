#include "LinkedListStack.hpp"
#include "FixedArrayStack.hpp"
#include "DynamicArrayStack.hpp"
#include "LinkedListQueue.hpp"
#include "ArrayQueue.hpp"

#include <map>
#include <stack>
#include <string>
#include <iostream>
#include <functional>

double calculate(const std::string& equation)
{
	static std::string chars("+-*/");
	
	static std::map<char, std::function<double(double,double)>> funcs {
		{'+', [&] (double x, double y) { return x + y; }},
		{'-', [&] (double x, double y) { return y - x; }},
		{'*', [&] (double x, double y) { return x * y; }},
		{'/', [&] (double x, double y) { return y / x; }}
	};
	
	std::stack<double> values;
	
	std::stack<char> operators;
	
	for (auto itr = equation.begin(), end = equation.end(); itr != end; )
	{
		if (std::isdigit(*itr))
		{
			auto start = itr;
			
			while(++itr != end && std::isdigit(*itr));
			
			values.push(std::stod(std::string(start, itr)));
			
			--itr;
		}
		
		else if (chars.find(*itr) != std::string::npos)
		{
			operators.push(*itr);
		}
		
		
		if (*(itr++) == ')' || itr == end)
		{
			double x = values.top();
			values.pop();
			
			double y = values.top();
			values.pop();
			
			values.push(funcs[operators.top()](x, y));
			
			operators.pop();
		}
	}
	
	return values.top();
}

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

int main(int argc, char * argv[])
{
	std::string equation;
	
	std::cout << "Enter an equation: ";
	
	std::getline(std::cin, equation);
	
	print(calculate(equation));
}
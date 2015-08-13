#include "WeightedQuickUnionPathCompression.hpp"

WeightedQuickUnionPathCompression::WeightedQuickUnionPathCompression(int N)
: WeightedQuickUnion(N)
{ }

int WeightedQuickUnionPathCompression::_getRoot(int i) const
{
	while (i != _roots[i])
	{
		_roots[i] = _roots[_roots[i]];
		
		i = _roots[i];
	}
	
	return i;
}
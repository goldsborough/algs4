#include "WeightedQuickUnion.hpp"

WeightedQuickUnion::WeightedQuickUnion(int N)
: QuickUnion(N)
, _sizes(new int[N])
{
	for (int i = 0; i < N; ++i)
	{
		_sizes[i] = 1;
	}
}

WeightedQuickUnion::~WeightedQuickUnion()
{
	delete [] _sizes;
}

void WeightedQuickUnion::makeUnion(int p, int q)
{
	int p_root = _getRoot(p);
	
	int q_root = _getRoot(q);
	
	if (p_root != q_root)
	{
		if (_sizes[p_root] >= _sizes[q_root])
		{
			_roots[q_root] = p_root;
			
			_sizes[p_root] += _sizes[q_root];
		}
		
		else
		{
			_roots[p_root] = q_root;
			
			_sizes[q_root] += _sizes[p_root];
		}
	}
}
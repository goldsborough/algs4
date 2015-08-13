#include "QuickUnion.hpp"

QuickUnion::QuickUnion(int N)
: UnionFind(N)
, _roots(new int[N])
{
	for (int i = 0; i < N; ++i)
	{
		_roots[i] = i;
	}
}

QuickUnion::~QuickUnion()
{
	delete [] _roots;
}

void QuickUnion::makeUnion(int p, int q)
{
	int p_root = _getRoot(p);
	
	int q_root = _getRoot(q);
	
	_roots[q_root] = p_root;
}

bool QuickUnion::connected(int p, int q) const
{
	return _getRoot(p) == _getRoot(q);
}

int QuickUnion::_getRoot(int i) const
{
	while (i != _roots[i])
	{
		i = _roots[i];
	}
	
	return i;
}


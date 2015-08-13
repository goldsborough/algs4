#include "QuickFind.hpp"

QuickFind::QuickFind(int N)
: UnionFind(N)
, _id(new int[N])
{
	for (int i = 0; i < N; ++i)
	{
		_id[i] = i;
	}
}

QuickFind::~QuickFind()
{
	delete [] _id;
}

void QuickFind::makeUnion(int p, int q)
{
	int p_id = _id[p];
	
	int q_id = _id[q];
	
	for (int i = 0; i < _N; ++i)
	{
		if (_id[i] == p_id)
		{
			_id[i] = q_id;
		}
	}
}

bool QuickFind::connected(int p, int q) const
{
	return _id[p] == _id[q];
}
#ifndef UNION_FIND_HPP
#define UNION_FIND_HPP

class UnionFind
{
public:
	
	UnionFind(int N)
	: _N(N)
	{ }
	
	virtual ~UnionFind() = default;
	
	virtual void makeUnion(int p, int q) = 0;
	
	virtual bool connected(int p, int q) const = 0;
	
protected:
	
	int _N;
};

#endif /* UNION_FIND_HPP */
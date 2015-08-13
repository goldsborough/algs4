#ifndef QUICK_UNION_HPP
#define QUICK_UNION_HPP

#include "UnionFind.hpp"

class QuickUnion : public UnionFind
{
public:
	
	QuickUnion(int N);
	
	virtual ~QuickUnion();
	
	virtual void makeUnion(int p, int q) override;
	
	virtual bool connected(int p, int q) const final;
	
protected:
	
	virtual int _getRoot(int i) const;
	
	int* _roots;
};

#endif /* QUICK_UNION_HPP*/

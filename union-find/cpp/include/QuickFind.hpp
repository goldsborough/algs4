#ifndef QUICK_FIND_HPP
#define QUICK_FIND_HPP

#include "UnionFind.hpp"

class QuickFind : public UnionFind
{
public:
	
	QuickFind(int N);
	
	virtual ~QuickFind();
	
	virtual void makeUnion(int p, int q) override;
	
	virtual bool connected(int p, int q) const override;
	
private:
	
	int* _id;
};


#endif /* QUICK_FIND_HPP */
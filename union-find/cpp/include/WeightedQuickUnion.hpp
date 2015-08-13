#ifndef WEIGHTED_QUICK_UNION_HPP
#define WEIGHTED_QUICK_UNION_HPP

#include "QuickUnion.hpp"

class WeightedQuickUnion : public QuickUnion
{
public:
	
	WeightedQuickUnion(int N);
	
	~WeightedQuickUnion();
	
	virtual void makeUnion(int p, int q) final;
	
protected:
	
	int* _sizes;
	
};

#endif /* WEIGHTED_QUICK_UNION_HPP */

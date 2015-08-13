#ifndef WEIGHTED_QUICK_UNION_PATH_COMPRESSION_HPP
#define WEIGHTED_QUICK_UNION_PATH_COMPRESSION_HPP

#include "WeightedQuickUnion.hpp"

class WeightedQuickUnionPathCompression : public WeightedQuickUnion
{
public:
	
	WeightedQuickUnionPathCompression(int N);
	
private:
	
	virtual int _getRoot(int i) const override;
};



#endif /* WEIGHTED_QUICK_UNION_PATH_COMPRESSION_HPP */

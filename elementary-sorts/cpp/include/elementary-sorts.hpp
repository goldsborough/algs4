#ifndef ELEMENTARY_SORTS_HPP
#define ELEMENTARY_SORTS_HPP

template<typename T>
struct remove_reference { typedef T type; };

template<typename T>
struct remove_reference<T&> { typedef T type; };

template<typename Itr>
void selection(Itr begin, Itr end)
{
	using T = typename remove_reference<decltype(*begin)>::type;
	
	for ( ; begin != end; ++begin)
	{
		Itr min = begin;
		
		for (Itr itr = begin + 1; itr != end; ++itr)
		{
			if (*itr < *min) min = itr;
		}
		
		T temp = *begin;
		
		*begin = *min;
		*min = temp;
	}
}

template<typename Itr>
void insertion(Itr begin, Itr end)
{
	using T = typename remove_reference<decltype(*begin)>::type;
	
	for (Itr i = begin+1; i != end; ++i)
	{
		T value = *i;
		
		Itr j = i, k = i - 1;
		
		for ( ; k >= begin && *k >= value; --j, --k)
		{
			*j = *k;
		}
		
		*j = value;
	}
}

template<typename Itr>
void shell(Itr begin, Itr end)
{
	using T = typename remove_reference<decltype(*begin)>::type;
	
	int h = 1;
	
	long N = end - begin;
	
	while (h < N/3) h = h * 3 + 1;
	
	while (h >= 1)
	{
		for (Itr i = begin + h; i < end; i += h)
		{
			T value = *i;
			
			Itr j = i, k = j - h;
			
			for ( ; k >= begin && *k >= value; j -= h, k -= h)
			{
				T temp = *k;
				
				*k = *j;
				
				*j = temp;
			}
			
			*j = value;
		}
		
		h /= 3;
	}
}

#endif /* ELEMENTARY_SORTS_HPP */
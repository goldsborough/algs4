import math

def smallestN(a, b, r):
	if not r:
		return None
	i = len(r)//2
	if a(r[i]) < b(r[i]):
		ret = smallestN(a, b, r[:i])
		return ret if ret is not None else r[i]
	else:
		return smallestN(a, b, r[i + 1:])

def largestN(a, b, r):
    	if not r:
    		return None
    	i = len(r)//2
    	if a(r[i]) > b(r[i]):
    		return largestN(a, b, r[:i])
    	else:
    		ret = largestN(a, b, r[i + 1:])
    		return ret if ret is not None else r[i]

if __name__ == "__main__":

	second = 10**6
	minute = 60 * second
	hour = 60 * minute
	day = 24 * hour
	month =  30.5 * day
	year = 12 * month
	century = 100 * year

	units = [second, minute, hour, day, month, century]

	f = [
		lambda n: 2 **n,
		lambda n: n * n ,
		lambda n: n,
		lambda n: math.sqrt(n),
		lambda n: n**(1/3),
		lambda n: math.log(n, 2)
	]

	print(0)







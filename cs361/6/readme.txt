Usage
	java PasswordGenerator order input characters passwords [seed]

Arguments
	order
		Generate passwords using nth-order entropy. Equivalently, look
		at n-grams while analyzing the input text. Set to 0 to generate
		random passwords.
	
	input
		Text file used for analyzing character frequency.
	
	characters
		Number of characters in each generated passwords.
	
	passwords
		Number of passwords to generate.
	
	seed
		Optional. Number to use as a seed to Java's random number
		generator.

Notes
	This program does not output any n-gram frequency tables. Since these
	were intended as debugging tools and I did not use them when debugging,
	I didn't bother putting them in after the fact. However, if frequency
	information is needed for grading, it is a one-liner to get debugging
	info. Just add `System.out.println(pg.ngrams);` after `pg.parse(f);`.

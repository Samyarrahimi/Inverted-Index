# InvertedIndex

This is an inverted index which is created by abstracts of persian documents in wikipedia.

# Some Examples
1-
سیاسی and اقتصادی

returns all documents that contain both of the words سیاسی اقتصادی

2-
سیاسی or اقتصادی

returns all documents that contain one or two of the words سیاسی اقتصادی

3-
سیاسی and اقتصادی and not ورزشی

returns all documents that contain both of the words سیاسی اقتصادی and not the word ورزشی

you can also query like this:

سیاسی ورزشی اقتصادی

which will give you the "and" of the words.

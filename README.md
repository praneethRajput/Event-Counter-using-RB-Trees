# Event-Counter-using-RB-Trees

Implementation of an event counter using red-black tree. Each event has two
fields: ID and count, where count is the number of active events with the given ID.
The event counter stores only those ID’s whose count is > 0. Once a count drops
below 1, that ID is removed. Initially the program builds red-black tree from
a sorted list of n events (i.e., n pairs (ID, count) in ascending order of ID) in O(n)
time. All the required operations are supported in the specified time complexity

Acronym : rbBST = Red Black Binary Search Tree will be used throughout the code

#pragma once

#include <vector>
#include <map>



class Node
{
public:

	struct compare {bool operator() (Node* a, Node* b) const{ return a->id < b->id; } };

	Node(int id,int name);
	Node(int id, int pre, int post, int component);

	int id;
	int name;
	int pre = -1;
	int post = -1;
	int component = -1;
	int e = 0;
	double CL = 0;

	std::map < Node*, int, compare> connections;

	void addConnection(Node* a);

};


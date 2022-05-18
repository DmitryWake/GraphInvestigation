#pragma once

#include <vector>
#include <map>

class Node
{

public:

	Node(int id,int name);
	Node(int id, int pre, int post, int component);

	int id;
	int name;
	int pre = -1;
	int post = -1;
	int component = -1;
	int e = 0;
	double CL = 0;

	std::map<Node*, int> connections;

	void addConnection(Node* a);

};


#pragma once
#include <unordered_map>
#include <algorithm>
#include <fstream>
#include <string>
#include <omp.h>
#include <math.h>
#include <iostream>

#include <boost/graph/adjacency_list.hpp>
#include <boost/graph/connected_components.hpp>
#include <boost/graph/strong_components.hpp>

#include "Node.h"

class Graph
{
private:
	std::unordered_map<int, int> dict;
	std::vector<Node*> nodes;
	std::vector<Node*> components;
	bool oriented;
	int countEdge = 0;
	Node* maxComp;

	double aCL, gCL;

	std::vector<int> comp;
	boost::adjacency_list<boost::vecS, boost::vecS, boost::directedS> g;

	Node* addNode(int name);

	void DFS(int &i, int comp, Node* node);	
public:
	Graph(bool oriented);
	~Graph();

	Node* operator[](int id);

	int getCountComp();
	int getCountNode();
	int getCountEdge();
	int getCountNodeInMaxComp();

	void clusterCoef(std::string filePath);

	void addConnection(int a, int b);
	void findComponents();
	long long findClique();

	int boostGetcountComp();

	void metaGraph(std::string filePath);

	void removeRandomX(int x);

	void sortByDeg();
	void pop_back(int x);


	void save(std::string filePath);
	void input(std::string filePath);
};


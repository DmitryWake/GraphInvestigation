#include "Graph.h"


Graph::Graph(bool oriented)
{
	this->oriented = oriented;
}

Graph::~Graph()
{
	for (auto i : nodes)
		delete i;
}

Node* Graph::operator[](int id)
{
	return nodes[id];
}

int Graph::getCountComp()
{
	return components.size();
}

int Graph::getCountNode()
{
	return nodes.size();
}

int Graph::getCountEdge()
{
	return countEdge;
}

int Graph::getCountNodeInMaxComp()
{
	int res = 0;
	int comp = maxComp->component;

	for (auto n : nodes)
		if (n->component == comp)
			res++;

	return res;
}

void Graph::clusterCoef(std::string filePath)
{
	for (auto n : nodes)
	{
		double k = 0;
		for (auto e : n->connections)
			k += e.second;

		if (k >= 2)
			n->CL = 2. * n->e / k / (k - 1);
		else
			n->CL = 0;
	}

	aCL = 0;
	for (auto n : nodes)
		aCL += n->CL;
	aCL /= nodes.size();

	double CnCL = 0, Cn = 0;
	for (auto n : nodes)
	{
		double Cnv = n->connections.size() * (n->connections.size() - 1) / 2;
		CnCL += n->CL * Cnv;
		Cn += Cnv;
	}

	gCL = CnCL / Cn;

	std::ofstream fout(filePath);
	
	fout << aCL << std::endl;
	fout << gCL << std::endl;

	for (auto n : nodes)
		fout << n->name << " " << n->CL << std::endl;

	fout.close();
}

void Graph::addConnection(int a, int b)
{
	Node* f = addNode(a);
	Node* s = addNode(b);
	
	f->addConnection(s);
	
	if (!oriented && f != s)
		s->addConnection(f);
		
	//boost::add_edge(f->id, s->id, g);
	++countEdge;
}

Node* Graph::addNode(int name)
{
	std::unordered_map<int,int>::iterator it = dict.find(name);
	if (it == dict.end())
	{
		it = dict.emplace(name, nodes.size()).first;
		Node* t = new Node(nodes.size(), name);
		nodes.push_back(t);
	}

	return nodes[(*it).second];
}

void Graph::DFS(int &i, int comp, Node* node)
{
	node->pre = i;
	node->component = comp;
	for (auto n : node->connections)
		if (n.first->pre == -1)
			DFS(++i, comp, n.first);

	node->post = ++i;
}


void Graph::findComponents()
{
	int max = 0;
	if (!oriented)
	{
		
		int comp = 0;
		int i = 0;

		for (auto n : nodes)
			if (n->component == -1)
			{
				components.push_back(n);
				DFS(i, comp, n);

				int size = components.back()->post - components.back()->pre + 1;
				if (size > max)
				{
					maxComp = n;
					max = size;
				}
				++comp;
			}
	}
	else
	{
		Graph rg(false);

		for (auto n : nodes)
		{
			Node* tn = new Node(n->id, n->name);
			rg.nodes.push_back(tn);
		}

		for (auto n : nodes)
			for (auto edge : n->connections)
				rg[edge.first->id]->connections.insert(std::make_pair(rg[n->id], 1));

		rg.findComponents();

		std::sort(rg.nodes.begin(), rg.nodes.end(),
			[](Node* a, Node* b) {return a->post >= b->post; });

		int comp = 0;
		int i = 0;		
		for (auto n : rg.nodes)
		{
			if (nodes[n->id]->component == -1)
			{
				components.push_back(nodes[n->id]);
				DFS(i, comp, nodes[n->id]);

				int size = components.back()->post - components.back()->pre + 1;
				if (size > max)
				{
					maxComp = nodes[n->id];
					max = size;
				}

				++comp;
			}
		}
	}
}

long long Graph::findClique()
{
	if (oriented)
		return -1;
	long long res = 0;

//#pragma omp parallel for reduction(+:res) num_threads(8)
	for (int i = 0; i < nodes.size(); i++)
	{
		Node* n1 = nodes[i];
		if (n1->connections.size() < 2)
			continue;
		for (auto n2 : n1->connections)
		{
			if (n2.first->connections.size() < 2 || n1->id > n2.first->id)
				continue;
			for (auto n3 : n2.first->connections)
			{
				if (n3.first == n1 || n3.first->id > n2.first->id || n3.first->id > n1->id)
					continue;

				for (auto n4 : n3.first->connections)
					if (n4.first == n1)
					{
						++n1->e;
						++n2.first->e;
						++n3.first->e;

						++res;
						break;
					}
			}
		}
	}

	return res;	
}

int Graph::boostGetcountComp()
{
	comp.resize(boost::num_vertices(g));

	return boost::strong_components(g, &comp[0]);
}

void Graph::metaGraph(std::string filePath)
{
	Graph res(oriented);

	for (int i = 0; i < components.size(); i++)
		res.addNode(i);

	for (auto n : nodes)
		for (auto e : n->connections)
			if (n->component != e.first->component)
				res[n->component]->connections[res[e.first->component]] += e.second;

	std::ofstream fout(filePath);

	std::vector<Node*> t(nodes);
	std::sort(t.begin(), t.end(),
		[](Node* a, Node* b) {return a->component < b->component; });

	fout << res.nodes.size() << std::endl; 

	for (int i = 0; i < t.size() - 1; i++)
	{
		fout << t[i]->name << " ";
		if (t[i]->component != t[i + 1]->component)
			fout << std::endl;
	}
	if(t.size() != 0)
		fout << t.back()->name << std::endl;

	for(auto n : res.nodes)
		for (auto e : n->connections)
			for (int i = 0; i < e.second; i++)
				fout << n->name << " " << e.first->name << std::endl;
		


	fout.close();
}

void Graph::removeRandomX(int x)
{

	for (int k = 0; k < x; k++)
	{
		int i = rand() % nodes.size();

		std::swap(nodes[i], nodes[nodes.size()-1]);
		nodes[i]->id = i;


		if (oriented)
			for (auto n : nodes)
				n->connections.erase(nodes.back());
		else
			for (auto n : nodes.back()->connections)
				if (n.first->id < nodes.size())
					nodes[n.first->id]->connections.erase(nodes.back());

		Node* t = nodes.back();
		nodes.pop_back();
		delete t;
	}

	for (auto n : nodes)
	{
		n->pre = -1;
		n->post = -1;
		n->component = -1;
	}

}

void Graph::sortByDeg()
{
	std::sort(nodes.begin(), nodes.end(),
		[](Node* a, Node* b) {return a->connections.size() < b->connections.size(); });

	for (int i = 0; i < nodes.size(); i++)
	{
		nodes[i]->id == i;
		dict[nodes.at(i)->name] = i;
	}
}

void Graph::pop_back(int x)
{
	for (int k = 0; k < x; k++)
	{
		if (oriented)
			for (auto n : nodes)
				n->connections.erase(nodes.back());
		else
			for (auto n : nodes.back()->connections)
				if (n.first->id < nodes.size())
					nodes[n.first->id]->connections.erase(nodes.back());

		Node* t = nodes.back();
		nodes.pop_back();
		delete t;
	}

	for (auto n : nodes)
	{
		n->pre = -1;
		n->post = -1;
		n->component = -1;
	}
}



void Graph::save(std::string filePath)
{
	std::ofstream fout(filePath);

	for (auto n : nodes)
		fout << n->name << " " << n->pre << " " << n->post << " " << n->component << std::endl;

	fout.close();
}

void Graph::input(std::string filePath)
{
	
}

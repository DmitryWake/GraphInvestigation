#include <iostream> 
#include <omp.h>
#include <string>

#include "Graph.h"
#include "Node.h"

using namespace::std;


int main()
{
	setlocale(0, "");

	string path[4];
	path[0] = "C:\\Users\\Dmitry\\OneDrive - St.Petersburg State University\\3 ���� 6 ���\\������ ������\\������� 1\\datasets\\CA-AstroPh.txt";
	path[1] = "C:\\Users\\Dmitry\\OneDrive - St.Petersburg State University\\3 ���� 6 ���\\������ ������\\������� 1\\datasets\\web-Google.txt";
	path[2] = "C:\\Users\\Dmitry\\OneDrive - St.Petersburg State University\\3 ���� 6 ���\\������ ������\\������� 1\\datasets\\vk-output.txt";
	path[3] = "C:\\Users\\Dmitry\\OneDrive - St.Petersburg State University\\3 ���� 6 ���\\������ ������\\������� 1\\datasets\\test4.txt";

	cout << "�������� �������: \n0: CA-AstroPh\n1: web-Google\n2: vk\n";

	int dataset; 
	cin >> dataset;

	cout << "���� ������...\n";
	ifstream fin(path[dataset]);

	int start = omp_get_wtime();
	Graph graph(false);
	Graph orGraph(true);

	while (!fin.eof())
	{
		int ida, idb;
		fin >> ida >> idb;

		graph.addConnection(ida, idb);
		if (dataset == 1)
			orGraph.addConnection(ida, idb);
	}

	cout << "������ ����������, ���� " << omp_get_wtime() - start << " ���.\n\n";

	cout << "�������� ������:\n0: A1\n1: A3\n2: A4\n3: B1\n4: B2\n";

	int z;
	cin >> z;

	double proc = 0.5;
	int x = graph.getCountNode() / 100 * proc;

	start = omp_get_wtime();
	switch (z)
	{
	case 0:
		graph.findComponents();

		cout << "1) ������ - " << graph.getCountNode() << endl;
		cout << "2) ����� - " << graph.getCountEdge() << endl;
		cout << "3) �����/�������� ����� - " << (double)graph.getCountEdge() / (graph.getCountEdge() / 2. * (graph.getCountEdge() - 1)) << endl;
		cout << "4) ��������� ������ ��������� - " << graph.getCountComp() << endl;
		cout << "5) ������ � ������������ ����������� ������ ��������� - " << graph.getCountNodeInMaxComp() << endl;
		cout << "6) 5) / 1) - " << (double)graph.getCountNodeInMaxComp() / graph.getCountNode() << endl;

		if (dataset == 1)
		{
			orGraph.findComponents();
			cout << "7) ����� ��������� ������� ��������� - " << orGraph.getCountComp() << endl;
			cout << "8) ������ � ������������ ���������� ������� ��������� - " << orGraph.getCountNodeInMaxComp() << endl;
			cout << "9) 8) / 1) - " << (double)orGraph.getCountNodeInMaxComp() / orGraph.getCountNode() << endl;
		}

		break;
	case 1:
		if (dataset != 1)
		{
			cout << "�������� ������� 1";
			break;
		}
		orGraph.metaGraph("metaGraph.txt");
		cout << "��������� ������� � ���� metaGraph.txt\n";
		cout << "1 ������ - n - ���-�� ��������� ������� ���������\n"
			<< "������ n �����.i ������ - ������ ������ � i ����������. ��������� � 0\n"
			<< "������ ����� � ��������� � ������ ������ � �������\n";

		break;
	case 2:
		cout << "����� ������������� - " << graph.findClique() << endl;
		graph.clusterCoef("clusterCoef.txt");
		cout << "��������� ������� � ���� clusterCoef.txt\n";
		cout << "1 ������ - ������� ���������� ����\n"
			<< "2 ������ - ���������� ���������� ����\n"
			<< "������ ������ ���. �������� ������� � ��������� ���������� ����\n";

		break;
	case 3:
	{
		ofstream f1("B1.txt");

		for (int i = 1; i <= 100; i++)
		{
			graph.removeRandomX(x);
			graph.findComponents();

			f1 << proc * i << " " << (double)graph.getCountNodeInMaxComp() / graph.getCountNode() << endl;
			cout << proc * i << " " << (double)graph.getCountNodeInMaxComp() / graph.getCountNode() << endl;
		}

		f1.close();
		cout << "��������� � ����� B1.txt\n";
	}

		break;
	case 4:
	{
		ofstream f2("B2.txt");

		graph.sortByDeg();
		for (int i = 1; i <= 100; i++)
		{
			graph.pop_back(x);
			graph.findComponents();

			f2 << proc * i << " " << (double)graph.getCountNodeInMaxComp() / graph.getCountNode() << endl;
			cout << proc * i << " " << (double)graph.getCountNodeInMaxComp() / graph.getCountNode() << endl;

		}
		f2.close();
		cout << "��������� � ����� B2.txt\n";
	}
		
		break;
	}

	cout << endl << "������������� �������: " << omp_get_wtime() - start << endl;

	fin.close();
	system("pause");
	return 0;
}
#include <iostream> 
#include <omp.h>
#include <string>

#include "Graph.h"
#include "Node.h"

using namespace::std;


int main()
{
	setlocale(0, "");

	srand(time(0));

	string path[8];
	path[0] = "C:\\Users\\Dmitry\\OneDrive - St.Petersburg State University\\3 курс 6 сем\\Теория графов\\Задание 1\\datasets\\CA-AstroPh.txt";
	path[1] = "C:\\Users\\Dmitry\\OneDrive - St.Petersburg State University\\3 курс 6 сем\\Теория графов\\Задание 1\\datasets\\web-Google.txt";
	path[2] = "C:\\Users\\Dmitry\\OneDrive - St.Petersburg State University\\3 курс 6 сем\\Теория графов\\Задание 1\\datasets\\vk-output.txt";
	path[3] = "C:\\Users\\Dmitry\\OneDrive - St.Petersburg State University\\3 курс 6 сем\\Теория графов\\Задание 1\\datasets\\CA-GrQc.txt";
	path[4] = "C:\\Users\\Dmitry\\OneDrive - St.Petersburg State University\\3 курс 6 сем\\Теория графов\\Задание 1\\datasets\\email-Eu-core.txt";
	path[5] = "C:\\Users\\Dmitry\\OneDrive - St.Petersburg State University\\3 курс 6 сем\\Теория графов\\Задание 1\\datasets\\socfb-Middlebury45.mtx";
	path[6] = "C:\\Users\\Dmitry\\OneDrive - St.Petersburg State University\\3 курс 6 сем\\Теория графов\\Задание 1\\datasets\\socfb-Reed98.mtx";
	path[7] = "C:\\Users\\Dmitry\\OneDrive - St.Petersburg State University\\3 курс 6 сем\\Теория графов\\Задание 1\\datasets\\soc-wiki-Vote.mtx";



	cout << "Выберите датасет: \n0: CA-AstroPh\n1: web-Google\n2: vk\n3: CA-GrQc\n4: email-Eu-core\n5: socfb-Middlebury45\n6: socfb-Reed98\n7: soc-wiki-Vote\n";

	int dataset; 
	cin >> dataset;

	cout << "Идет чтение...\n";
	ifstream fin(path[dataset]);

	int start = omp_get_wtime();
	Graph graph(false);
	Graph orGraph(true);

	while (!fin.eof())
	{
		int ida, idb;
		fin >> ida >> idb;

		graph.addConnection(ida, idb);
		if (dataset == 1 || dataset == 7 || dataset == 4)
			orGraph.addConnection(ida, idb);
	}

	cout << "Чтение завершенно, ушло " << omp_get_wtime() - start << " сек.\n\n";

	cout << "Выберите задачу:\n0: A1\n1: A3\n2: A4\n3: B1\n4: B2\n";

	int z;
	cin >> z;

	double proc = 1;
	int x = graph.getCountNode() / 100 * proc;

	start = omp_get_wtime();
	switch (z)
	{
	case 0:
		graph.findComponents();

		cout << "1) Вершин - " << graph.getCountNode() << endl;
		cout << "2) Ребер - " << graph.getCountEdge() << endl;
		cout << "3) Ребер/максимум ребер - " << (double)graph.getCountEdge() / (graph.getCountNode() / 2. * (graph.getCountNode() - 1)) << endl;
		cout << "4) Компанент слабой связности - " << graph.getCountComp() << endl;
		cout << "5) Вершин в максимальной компаненете слабой связности - " << graph.getCountNodeInMaxComp() << endl;
		cout << "6) 5) / 1) - " << (double)graph.getCountNodeInMaxComp() / graph.getCountNode() << endl;
		cout << "boost - " << graph.boostGetcountComp() << endl;

		if (dataset == 1 || dataset == 7 || dataset == 4)
		{
			orGraph.findComponents();
			cout << "7) Число компонент сильной связности - " << orGraph.getCountComp() << endl;
			cout << "8) Вершин в максимальной компаненте сильной связности - " << orGraph.getCountNodeInMaxComp() << endl;
			cout << "9) 8) / 1) - " << (double)orGraph.getCountNodeInMaxComp() / orGraph.getCountNode() << endl;
		}

		break;
	case 1:
		if (dataset != 1)
		{
			cout << "Выберете датасет 1";
			break;
		}
		orGraph.findComponents();
		orGraph.metaGraph("metaGraph.txt");
		cout << "Результат записан в файл metaGraph.txt\n";
		cout << "1 строка - n - кол-во компонент сильной связности\n"
			<< "Дальше n строк.i строка - список вершин в i компоненте. Нумерация с 0\n"
			<< "Дальше ребра в метаграфе с учетом разных и кратных\n";

		break;
	case 2:
		cout << "Число треугольников - " << graph.findClique() << endl;
		cout << endl << "Потребовалось времени: " << omp_get_wtime() - start << endl;
		graph.clusterCoef("clusterCoef.txt");
		cout << "Результат записан в файл clusterCoef.txt\n";
		cout << "1 строка - средний кластерный коэф\n"
			<< "2 строка - глобальный кластерный коэф\n"
			<< "Дальше список пар. Название вершины и локальный кластерный коэф\n";

		break;
	case 3:
	{
		ofstream f1("B1.txt");

		for (int i = 1; i < 100; i++)
		{
			graph.removeRandomX(x);
			graph.findComponents();

			f1 << proc * i << " " << (double)graph.getCountNodeInMaxComp() / graph.getCountNode() << endl;
			cout << proc * i << " " << (double)graph.getCountNodeInMaxComp() / graph.getCountNode() << endl;
		}

		f1.close();
		cout << "Результат в файле B1.txt\n";
	}

		break;
	case 4:
	{
		ofstream f2("B2.txt");

		graph.sortByDeg();
		for (int i = 1; i < 100; i++)
		{
			graph.pop_back(x);
			graph.findComponents();

			f2 << proc * i << " " << (double)graph.getCountNodeInMaxComp() / graph.getCountNode() << endl;
			cout << proc * i << " " << (double)graph.getCountNodeInMaxComp() / graph.getCountNode() << endl;

			if (i == 85)
				cout << " ";
		}
		f2.close();
		cout << "Результат в файле B2.txt\n";
	}
		
		break;
	}

	cout << endl << "Потребовалось времени: " << omp_get_wtime() - start << endl;

	fin.close();
	system("pause");
	return 0;
}
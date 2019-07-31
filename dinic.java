class dinic{
	int[] level;
	ArrayList<edge>[] graph;
	TreeSet<edge>[] aug_graph;
	long[][] weight;
	int size;
	dinic(int n){
		weight=new long[n][n];
		size=n;
		level=new int[n];
		aug_graph=new TreeSet[n];
		graph=new ArrayList[n];
		for(int i=0;i<n;i++) {
			aug_graph[i]=new TreeSet<edge>(new a());
			graph[i]=new ArrayList<edge>();
		}
	}
	public boolean hasflow(int s,int t) {
		Queue<Integer> q=new LinkedList<Integer>();
		Arrays.fill(level, -1);
		level[s]=0;
		q.add(s);
		while(q.size()!=0) {
			int pop=q.remove();
			for(edge e:this.aug_graph[pop]) {
				if(level[e.value]==-1 && weight[pop][e.value]>0) {
					level[e.value]=level[pop]+1;
					q.add(e.value);
				}
			}
		}
		if(level[t]==-1) {
			return false;
		}
		else {
			return true;
		}
	}
	public void make_augmented_graph() {
		for(int i=0;i<size;i++) {
			for(edge j:this.graph[i]) {
					if(aug_graph[i].contains(new edge(j.value,0,0))) {
						aug_graph[i].floor(new edge(j.value,0,0)).weight+=j.capacity;
					}
					else {
					aug_graph[i].add(new edge(j.value,j.capacity,0));
					}
					if(aug_graph[j.value].contains(new edge(i,0,0))) {
						
					}
					else {
					aug_graph[j.value].add(new edge(i,0,0));
					}
			}
		}
		for(int i=0;i<size;i++) {
			for(edge j:this.aug_graph[i]) {
				weight[i][j.value]=j.weight;
			}
		}
	}
	public long sendflow(int s,int t,long flow) {
		if(s==t) {
			return flow;
		}
		for(edge e:this.aug_graph[s]) {
			if(e.value!=s && this.level[e.value]==this.level[s]+1 && weight[s][e.value]>0) {
			long send=Math.min(flow, weight[s][e.value]);
			long ret=this.sendflow(e.value, t, send);
			if(ret>0) {
				weight[s][e.value]-=ret;
				weight[e.value][s]+=ret;
				return ret;
			}
			}
		}
		return 0;
	}
	public long ans(int s,int t) {
		this.make_augmented_graph();
		
		long flow=0;
		while(this.hasflow(s, t)) {
			long temp=1;
			while(temp>0) {
				temp=this.sendflow(s, t, Integer.MAX_VALUE);
				flow+=temp;
				
			}
		}
		//System.out.println(loop);
		return flow;
	}
	public void add_edge(int s,int d,long val) {
		if(s!=d) {
		this.graph[s].add(new edge(d,0,val));
		}
	}
class a implements Comparator<edge>{
	public int compare(edge e1,edge e2) {
		return e1.value-e2.value;
	}
}
class edge{
	int value;
	long weight;
	long capacity;
	edge(int value,long weight,long capacity){
		this.value=value;
		this.weight=weight;
		this.capacity=capacity;
	}
}
}

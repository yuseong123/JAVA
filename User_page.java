package project;
import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
/*
 판매된 음료 8종류 
 믹스커피 200원 , 고급믹스커피 300원 , 물 450원, 캔커피 500원, 이온음료 550원 ,고급캔커피 700원, 탄산음료 750원 , 특화음료 800원

◻ 각각 음료의 재고는 기본적으로 10개로 하며, 10개 이상 음료가 배출되었을 때는 해
당 음료 (물품)에 대해 품절 표시를 하고 해당 음료를 더 이상 선택할 수 없어야 한다 
(단, 이전에 재고가 보충되었을 경우에는 해당 재고만큼의 판매가 가능해야 함).

◻ 입력 받을 수 있는 화폐의 단위는 10원, 50원, 100원, 500원, 1,000원으로만 한정하
되, 지폐로 입력 받을 수 있는 금액의 상한선은 5,000원까지이며, 지폐와 동전을 모두 
포함하여 총 7,000원을 초과하여 입력 받을 수 없다 (화폐의 입력 금액에 따라 판매 가
능 음료가 표기되어야 한다). 
이 때, 화폐를 입력 받는 변수는 반드시 동적 할당을 사
용해야 하며, 화폐가 반환되거나 음료의 판매가 끝나면 동적 할당을 해제해야 한다.
   
◻ 기본적으로 거스름 돈을 가지고 있어야 하며 (각 동전별로 10개를 기본 값으로 하고 
음료의 개수와 마찬가지로 생성자를 사용하여 초기화 할 것), 거스름 돈 반환 혹은 음
료 판매에 따른 동전의 가감이 구현되어야 한다. 또한 음료 자판기에서 화폐를 입력 받
을 때는 반드시 사용자 요구에 의한 화폐 반환이 가능해야 한다. 

단, 거스름돈이 없는 경우, 거스름돈 없음이란 표기를 한 후 이에 맞는 화폐 입력을 받아야 한다.
◻ 음료가 배출된 이후에도 반드시 다시 화폐를 입력 받을 수 있는 상태가 되어야 한다.
*/

public class User_page {
	public static int[][]money=new int[5][3];
	static {
		for(int i=0;i<5;i++) {
			money[i][0]=10;
			money[i][1]=10;
			money[i][2]=0;
		}
	}
	
	//1열 처음부터 사람이 보유한 화폐 수, 2열 처음부터 자판기가 보유한 화폐 수, 3열은 자판기에 투입 기록 ,4열은 미정
	//행은 전부 각 금액별로 분류한 것
	//0열로 사람이 보유한 화폐 수 교체, 1열은 자판기가 보유한 화폐수로 교체 ,3열은 자판기에 돈 투입 기록으로 교체 4열은 삭제
	public static int[] money_unit= {10,50,100,500,1000};

}

class Drink_Node{		//링크드 리스트 구현
	String name;
	int price;
	int count;
	Drink_Node next;	//자바는 객체지향이라 *를 안 붙여도 되지만 c는 절차지향이라 *가 붙어야함.
	
	public Drink_Node(String name, int price, int count) {
		this.name=name;
		this.price=price;
		this.count=count;
		this.next=null;
	}
}

class Linked_list {					//음료 링크드 리스트 (삽입과 선형탐색)
	public Drink_Node head;
	
	public Linked_list() {
		head=null;
	}
	
	public void insert_node(String name,int price, int count) {//맨 마지막에 음료 정보를 갖는 노드 삽입
		Drink_Node newNode = new Drink_Node(name,price,count);
		if(head==null) {
			this.head=newNode;
		}
		else {
			Drink_Node temp=head;
		while(temp.next!=null) {
			temp=temp.next;		//다음 노드로로 이동
		}
		temp.next=newNode;		//마지막 노드에 새 정보를 가진 노드 연결
		}
	}
	
	//음료 이름으로 해당 음료 노드 찾기
	public Drink_Node find_drink(String name) {
		/*만약 찾을 경우 해당 값의 결과 반환을 위해 반환형으로 노드를 지정
		  1번재 노드에 있을 수도 있기에 일단 확인 후 없으면 다음 노드로 이동*/
		Drink_Node find_temp=head;

		while(find_temp!=null) {
			if(name.equals(find_temp.name)) {//이름을 찾았을 경우
				return find_temp;
			}
			find_temp=find_temp.next;		//다음 노드로 이동
		}
		return null;						//없으면 널 반환과 못 찾았다는 메시지 출력
	}
}									//음료 링크드 리스트 부분 끝 (삽입과 선형탐색)


class queue_node {
	String drink_name;
	queue_node next;
	
	public queue_node(String drink_name) {
		this.drink_name =drink_name;
		this.next=null;
	}
}

class drink_Queue{		//음료재고 보충 , 배열로 구현 시 음료 구매 개수를 한정적으로 저장할 수 있어서 연결리스트로 재구현
	queue_node front;
	queue_node rear;
	
	drink_Queue(){
		front=null;
		rear=null;
	}
	
	public void input_infor(String name) {
		//매개변수로 정수만 받을 경우
		//해당 변수의 첫 자리는 판매된 음료 노드의 번호, 나머지 자리는 판매된 음료 개수
		//0801 이라면 8번째 노드의 음료가 1개 판매된 경우
		//원래는 정수 변수를 매개변수로 받아서 해당 음료 노드 번호랑 판매된 개수를 받아서 저장하려고 함
		queue_node new_node=new queue_node(name);
		if(rear==null) {	//음료 구매를 처음 한 경우
			front=new_node;
			rear=new_node;
		}
		else {
			rear.next=new_node;	//다음 노드로 이동
			rear=new_node;		//정보 삽입
		}
		
	}
	
	public String refill() {
		String name;
		if(front==null) {//구매된 것이 없는 경우
			return null;
		}
		//구매한 게 있어서 리필할 경우
		name= front.drink_name;
		front=front.next;
		return name;
	}
}

class vending{
	public int inserted_money=0;
	drink_Queue queuetype = new drink_Queue();	//queue에 정보를 넘기기 위한 인스턴스(객체) 생성
	
	public void buying(Drink_Node drink) {
		if(drink.count<=0 || drink.price > inserted_money) {
			//재고가 부족하거나 사용자의 돈이 부족해서 결제가 안될 경우 팝업 메시지 출력
		}
		else {
			//해당 시간은 기록X, 날짜만 기록O / 재고 관련 파일의 개수를 줄인다.
			inserted_money-=drink.price;//물건 가격만큼 투입된 돈 삭제
			drink.count--;
			queuetype.input_infor(drink.name);
			System.out.println(drink.name+"물건 구매 완료");
			//물건이 구매되었다는 메시지 출력
			//지폐에 투입된 화폐도 제거
			
		}	
	}
	
	public void insert_money(int select) {
		int selected=-1;
		int money=0;	//당장 넣으려는 돈
		money=User_page.money_unit[select];
		//소지한 사람의 화폐 수가 0일 경우 투입 불가, 화폐포함 5천원 이상이면 투입 불가, 동전만 7천원 이상이면 투입불가
		if(User_page.money[select][0]<=0) {//사용자의 보유금액 부족시
			return ;
		}
		
		else if(inserted_money+1000>5000 && select==4) {//지폐 1장 이상이 이미 투입된 경우, 투입할 금액 5천원 이상시 투입 불가
			return ;
		}
		
		else if(inserted_money+money>7000) {//7000원 이상 투입시 추가 투입 불가
			return ;
		}
			
		else {//정상 투입 상황
			inserted_money+=money;
			User_page.money[select][0]--;//사람 보유 화폐수 감소
			User_page.money[select][1]++;//자판기 보유 화폐수 증가
			User_page.money[select][2]++;//투입기록 추가
		}
	}	
}

class pw_tree {							//비밀번호 (트리 구조 사용)
		String password="qwer1234!";
		pw_tree left;
		pw_tree right;
		
		public pw_tree(String pw) {
			this.password=pw;
			this.left=null;	//성공할 경우 이동
			this.right=null;//실패할 경우 이동
		}
		private boolean check_password(String pw) {
			if (pw.length() < 8) {//8자리 미만일 경우 멈춤
				return false;
			}
        	String specialChars = "!@#$%^&*()-=_+?.,;";//특수기호 판단
        	
        	if(pw.matches(".*[0-9].*")&&pw.matches(".*[!@#$%^&*()\\-=_+?.,;].*")) {
        			return true;
                }
        		else {
                    System.out.println("숫자혹은 특수문자가 포함되어 있지 않습니다.");
                    return false;
                }
		}
		
		private void Enter_password(String pw) {
			if(this.password.equals(pw)) {//관리자 페이지 열기
				
			}
		}
		
		public void Change_password(String pw) {
			if(check_password(pw)) {
				password=pw;
				System.out.println("비밀번호 교체 성공.");
			}
			else {
				System.out.println("비밀번호 교체 실패");
			}
		}
}

class STACK{		//투입된 돈 반환
	int size;
	int []stack;
	
	STACK(int size){
		this.size=size;
		stack=new int[size];
	}
	public void push(int item) {//돈을 투입할 때, 해당 내용들을 스택에 넣고 나중에 수금은 아니고 
		int j=0;
		for(int i=4;i>=0;i--) {	//1000,500,100,50,10원 순으로 저장
			j=item/User_page.money_unit[i];
			item%=User_page.money_unit[i];
			stack[i]+=j;
		}
	}
	
    public int pop() {
    	int j=0,total=1000*stack[4]+500*stack[3]+100*stack[2]+50*stack[1]+10*stack[0];
    	for(int i=4;i>=0;i--) {
    		j=stack[i];
    		System.out.println(User_page.money_unit[i]+"원이"+j+"장 반환되었습니다.");
    		User_page.money[i][0]+=j;//사람에게 복구
			User_page.money[i][1]-=j;//자판기 소유한 화폐 수 차감
			User_page.money[i][2]-=j;//투입 기록 차감
			stack[i]=0;
    	}
    	System.out.println("금액"+total+"원이 반환되었습니다.");
    	
    	return j;
    }
}

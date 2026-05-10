package project;
import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
/*
관리자 메뉴 (비밀번호 확인을 통해 관리자 메뉴에 접근할 수 있도록 작성하되, 비밀
번호는 반드시 특수문자 및 숫자가 각각 하나 이상 포함된 8자리 이상으로 설정할 수 
있도록 해야 하며 필요시 언제든 변경 가능해야 함)는 다음과 같은 조건을 만족해야 한
다.
      
- 일별/월별 매출 산출, 각 음료의 일별/월별 매출, 각 음료의 재고 보충 (재고 보충은 
오프라인 형태)이 가능해야 한다. 이 때, 일별/월별 매출은 사전에 파일로 저장해 놓
은 파일(각자 만들어 사용)을 사용하여야 하며, 현재의 자판기 매출은 사전에 저장해 
놓은 파일과 연관성을 가지고 있어야 한다.

- 관리자 메뉴에서는 현재 자판기 내의 화폐현황을 손쉽게 파악할 수 있어야 하며, 관
리자가 “수금”이란 메뉴를 선택할 경우, 해당 금액을 수금할 수 있어야 한다. 단, 이 
경우에도 반환을 위한 최소한의 화폐 (임의로 지정할 것)는 남겨두어야 한다.

- 관리자 메뉴에서는 각 음료의 판매가격, 판매이름을 사용자의 입력을 통해 언제든 바
꿀 수 있어야 한다.

- 관리자 메뉴와 관련된 모든 사항들은 파일로 읽기/쓰기가 되어야 한다. (최소 기록 
사항: 일별/월별 매출, 재고 소진 날짜 혹은 주기)

◻ 관리자 메뉴가 활성화 되어 있는 경우, 화면이 관리자 전용 메뉴로 변경 되어야 하며, 
관리자 메뉴가 활성화 되어 있는 경우에는 다른 기능들은 동작하지 않아야 한다. 즉, 
관리자 화면과 판매 화면은 각각 독립적으로 동작해야 한다.
 */
public class Admin {
	//비밀번호로 로그인 기능 구현(추가적으로 변경 기능 구현)
	//음료 재고 보충, 매출 읽기 쓰기
	//화폐 현황 파악, 수금 기능 구현 (최소한의 반환할 돈은 남겨둬야 함)
	//음료의 가격, 제품명을 변경 가능해야함
	public Admin() {
		
	}
}


class drink_Queue{		//음료재고 보충 #판매된 개수와 해당 음료의 구분자를 가져야 함
	int front;
	int rear;
	Drink_Node []queue;
	int Queuesize=9;
	
	drink_Queue(){
		this.front=0;
		this.rear=0;
		queue=new Drink_Node[Queuesize];
		
	}
	
	private boolean is_full() {
		if((rear+1)%Queuesize == front ) {
			return false;
		}
		return true;
	}
	
	public void input_infor(String name) {
		//해당 변수의 첫 자리는 판매된 음료 노드의 번호, 나머지 자리는 판매된 음료 개수
		//0801 이라면 8번째 노드의 음료가 1개 판매된 경우
		//원래는 정수 변수를 매개변수로 받아서 해당 음료 노드 번호랑 판매된 개수를 받아서 저장하려고 함
		
		for(int i=0;i<rear;i++) {			//큐에 저장된 개수 만큼만 반복
			if(queue[rear].equals(name)) {	//큐에 기존의 정보가 있어서 개수만 추가할 경우
				queue[rear].count++;
			}
			else {							//큐에 기존 정보가 없어서 이름부터 새로 넣을 경우
				queue[rear].name=name;
				queue[rear].count++;
			}
			rear++;
		}
	}
	
	public void refill(Drink_Node d) {
		while(front!=rear) {
			front++;
			if(d.name.equals(queue[front].name)) {	//이름이 동일할 경우
				d.count+=queue[front].count;		//큐의 판매된 양을 노드에 삽입함.
			}
		}
	}
}


//화폐 현황 파악 , 수금(최소한의 거스름 돈은 남겨둘 것
class checking {
	checking() {
		checking c=new checking();
		c.show(User_page.money_unit,User_page.money);
		c.collect_money(User_page.money_unit,User_page.money);
	}
	
	private void show(int[] unit,int[][] money_array) {
		for(int i=0;i<5;i++) {
			System.out.println(unit[i]+money_array[i][2]);//현재 자판기에 투입된 지폐 수
		}
	}
	
	private void collect_money(int[] unit,int[][] money_array) {
		for(int i=0;i<5;i++) {
			if(money_array[i][2]>10) {
				money_array[i][0]++;
				money_array[i][1]--;
				money_array[i][2]--;
				System.out.println(unit[i]+"원이 수금되었습니다.");
			}
		}
	}
	
}

//음료의 판매 가격과 제품 명을 바꿀 수 있어야 한다
class change{
	change(){
	}
	
	public Drink_Node changing(Drink_Node head,String new_name, int new_price,String origin_word) {
		Drink_Node temp=head;									//기존의 노드들을 알려줄 객체
		
		while(temp!=null) {		//바꿀 정보를 가진 노드를 찾을 때까지 반복
			if(temp.name.equals(origin_word)) {//조건이 맞아 바꿀 경우
				temp.name=new_name;
				temp.price=new_price;
				return temp;
			}
			temp=temp.next;
		}
		System.out.println(origin_word+"해당 제품은 없으니 다른 제품을 변경하여 주세요.");
		return null;
	}
}
//파일로 기록되어 있어야 함
/*
private void day_save_sale(Drink_Node drink) {//시간정보 받기
	int day_total_money=0;	//일별 총 매출

	LocalDateTime now=LocalDateTime.now();	//현재 날짜와 시간 가져옴
	String folderName=now.format(DateTimeFormatter.ofPattern("yyyy_MM"));	//해당 월에 대한 폴더 생성
	File directory =new File(folderName);
	if(!directory.exists()) {
		directory.mkdir();		//다음 달로 넘어갈 경우, 새 디렉토리 생성
	}							//해당 연월을 가진 '폴더'생성
	
	String fileName=now.format(DateTimeFormatter.ofPattern("dd"))+".txt";	//사람용 당일 판매데이터를 가진 파일 생성
	File file=new File(folderName,fileName);
	
	//사람용 당일 판매 데이터 파일
	try(BufferedWriter writer=new BufferedWriter(new FileWriter(file,true))){
		writer.write("팔린 물건: "+ drink.name +" | "+"가격: "+drink.price);
		writer.newLine();
		}
	catch(IOException e) {
		e.printStackTrace();	//에러 메시지 출력
	}
	
	//일 매출 파일(서버 기록용)
	String fileName1=now.format(DateTimeFormatter.ofPattern("dd"))+".DB"+".txt";	//DB용 당일 판매데이터를 가진 파일 생성
	File file1=new File(folderName,fileName1);
	try(BufferedWriter writer=new BufferedWriter(new FileWriter(file1,true))){
		day_total_money+=drink.price;
		writer.write(drink.price+","+day_total_money);		//일 매출까지 ,로 구분 후 저장. 예:1000,10000
		writer.newLine();
		
	}
	catch(IOException e) {
		e.printStackTrace();	//에러 메시지 출력
	}
}

public void month_save_sale(Drink_Node drink) {
	LocalDateTime now=LocalDateTime.now();	//현재 날짜와 시간 가져옴
	int month_total_money=0;	//월별 총 매출
	String line;				//일 매출 파일에서 한 줄을 읽고 임시 저장할 변수
	String []result;			//일 매출만 저장할 변수
	//String month_total=folderName+"record.txt";								//월 매출용 파일 생성
	//File month_total_file=new File(folderName,month_total);
	try(BufferedReader br=new BufferedReader(new FileReader("file.txt"))){
		while((line=br.readLine())!=null) {
			result=line.split(",");
			month_total_money+=Integer.parseInt(result[1]);	//파일의 일매출들을 전부 읽고 해당 것들을 전부 변수에 저장.
		}
		BufferedWriter bw=new BufferedWriter(new FileWriter("MM_sale.txt",true));
		bw.write("월 매출:"+month_total_money);
		bw.newLine();
	}
	catch(IOException e) {
		e.printStackTrace();
	}
}*/

//관리자 페이지가 작동하는 동안은 다른 페이지는 작동 불가
/*
// 관리자 버튼 클릭 시
public void openAdmin(JFrame currentFrame) {
    currentFrame.dispose();      // 현재 창 닫기
    new AdminFrame().setVisible(true);  // 관리자 창 열기
}

// 관리자 종료 시
public void closeAdmin(JFrame adminFrame) {
    adminFrame.dispose();        // 관리자 창 닫기
    new UserFrame().setVisible(true);   // 사용자 창 열기
}
*/

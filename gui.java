package project;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
public class gui {
	public void initUI() {			//사용자 gui
		setLayout(new BorderLayout());
        //위: 투입한 금액 표시
        balanceLabel = new JLabel("현재 투입금액: "+inserted_Money+"원");		//일단 0원으로 표시
        add(balanceLabel, BorderLayout.NORTH);
        
        //왼쪽: 각종 메시지 출력 (구매,품절 등) 
        messageArea = new JTextArea(15, 15);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane, BorderLayout.WEST);
        
        //중앙: 음료 패널
        drinkPanel = new JPanel();
        drinkPanel.setBorder(BorderFactory.createTitledBorder("음료 선택"));
        add(drinkPanel, BorderLayout.CENTER);
        
        //아래: 금액 버튼
        coinPanel = new JPanel();
        for (int i = 0; i < moneyUnit.length; i++) {
            final int coinVal = moneyUnit[i];
            JButton coinButton = new JButton(coinVal + "원 투입");
            coinButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    insertCoin(coinVal);
                }
            });
            coinPanel.add(coinButton);
        }
        add(coinPanel, BorderLayout.SOUTH);

        //오른쪽: 환불 버튼 + 관리자 버튼
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new GridLayout(2, 1, 5, 5));
        
        JButton refundBtn = new JButton("반환");
        refundBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnCoin();
            }
        });
        eastPanel.add(refundBtn);
        
        JButton adminBtn = new JButton("관리자");
        adminBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	login_page(); // 관리자 로그인 창 호출
            }
        });
        eastPanel.add(adminBtn);
        
        add(eastPanel, BorderLayout.EAST);
        updateBtns();
        

    }
	public Activate() {
		
	}
	
	public Cant_activate() {
		
	}
}


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WeatherMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread th = new Thread() {
			public void run() {
				// 스레드로 수행할 내용
				try {
					// 다운로드받을 URL 생성
					URL url = new URL(
							"http://www.hani.co.kr/rss/");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					// 옵션 설정
					// 캐시 설정 여부 - 다운로드 받아 놓고 다음에 다운 받은 데이터를 쓸 것인지?
					con.setUseCaches(false);
					// 얼마동안 접속을 시도해 볼 것인지 설정
					con.setConnectTimeout(30000);

					// 문자열을 읽기 위한 스트림 생성
					BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

					// 줄 단위로 데이터를 읽어서 sb에 추가
					StringBuilder sb = new StringBuilder();
					while (true) {
						String line = br.readLine();
						if (line == null)
							break;
						sb.append(line);
					}

					// sb의 내용을 xml에 대입
					String xml = sb.toString();
					
					// xml문자열을 파싱할 수 있는 객체 생성
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					
					//파싱할 문자열을 스트림으로 변환
					InputStream is = new ByteArrayInputStream(xml.getBytes());
					
					// 문자열을 파싱 수행
					Document doc = builder.parse(is);
					
					// 루트 찾기
					Element root = doc.getDocumentElement();

					// tmx 태그 전부 찾기
					NodeList list = root.getElementsByTagName("title");
					
					int i = 0;
					while(i<list.getLength()) {
						Node item = list.item(i);
						// 첫번째 자식 찾기
						Node child = item.getFirstChild();
						// 데이터 찾기
						String tmx = child.getNodeValue();
						System.out.println(tmx);
						i++;
					}
					
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		};
		th.start();
	}

}

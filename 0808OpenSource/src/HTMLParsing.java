import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParsing {

	public static void main(String[] args) {
		// 스레드 만들기
		Thread th = new Thread() {
			public void run() {
				try {
					// 다운로드받을 주소 만들기
					URL url = new URL("https://tv.naver.com/");
					// 다운로드 받을 주소와 연결하는 객체 만들기
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					// 옵션 설정
					con.setUseCaches(false);
					con.setConnectTimeout(30000);
					// 문자열을 읽을 수 있는 스트림 생성
					BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

					// 다운로드받는 문자열을 중간 저장할 인스턴스 생성
					// String에 바로 추가하게 되면 메모리 낭비가 발생한다.
					// String은 자기 자신에게 추가할 수 없기 때문에 기존 내용을 복사해서 추가.
					StringBuilder sb = new StringBuilder();
					while (true) {
						// 한 줄 읽기
						String line = br.readLine();
						if (line == null)
							break;
						sb.append(line + "\n");
					}
					// 다 읽은 데이터를 String으로 변환
					String html = sb.toString();
					// sb 메모리 정리
					sb = null;
					// 연결객체 정리
					br.close();
					con.disconnect();

					// HTML 루트를 찾아줌.
					Document doc = Jsoup.parse(html);
					Elements elements = doc.getElementsByTag("tooltip");
					// Elements elements = doc.getElementsByClass("screen_out");
					int i = 0;
					while (i < elements.size()) {
						// 데이터 한 개 가져오기
						Element element = elements.get(i);
						// 태그 안의 내용 출력
						System.out.println(element.text());
						i++;
					}

//					Element element = doc.getElementById("mainServiceTitle");
//					System.out.println(element.text());

				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		};
		th.start();
	}
}

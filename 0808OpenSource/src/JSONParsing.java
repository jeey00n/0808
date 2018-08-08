// 다음의 책 검색 API 주소에서 author값만 추출하기

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParsing {

	public static void main(String[] args) {
		Thread th = new Thread() {
			public void run() {
				// 스레드로 수행할 내용
				try {
					// 다운로드받을 URL 생성
					URL url = new URL(
							"http://apis.daum.net/search/book?apikey=465b06fae32febacbc59502598dd7685&output=json&q=java\r\n");
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

					// sb의 내용을 json에 대입
					String json = sb.toString();

					// JSON 문자열 파싱
					JSONObject main = new JSONObject(json);

					// channel키의 데이터를 JSONObject 타입으로 가져오기
					JSONObject channel = main.getJSONObject("channel");

					// channel에서 item키의 값을 배열로 가져오기
					JSONArray item = channel.getJSONArray("item");

					// 배열을 순회
					int i = 0;
					while(i<item.length()) {
						JSONObject book = item.getJSONObject(i);
						String author = book.getString("author");
						System.out.println(author);
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


// 강남역 근처 위도와 경도를 받아서 관광명소를 출력

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParsing1 {

	public static void main(String[] args) {
		Thread th = new Thread() {
			public void run() {
				// 스레드로 수행할 내용
				try {
					// 다운로드받을 URL 생성
					URL url = new URL(
							"https://apis.daum.net/local/v1/search/category.json?apikey=465b06fae32febacbc59502598dd7685&code=AT4&location=37.514322572335935,127.06283102249932&radius=20000");
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

					JSONObject channel = main.getJSONObject("channel");
					JSONArray ar = channel.getJSONArray("item");
					int i = 0;
					while (i < ar.length()) {
						JSONObject temp = ar.getJSONObject(i);
							System.out.println(temp.getString("title") + ": " + temp.getString("address"));
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

package ngo.vnexpress.reader;

import java.io.IOException;

import ngo.vnexpress.reader.BasicFunctions.BasicFunctions;
import ngo.vnexpress.reader.Fragments.DisplayWebNewsFragment;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import com.google.analytics.tracking.android.Log;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoadWeb extends AsyncTask<String, String, String> {
	String link;

	private Document doc;
	private WebView webView;
	private boolean isLoadedFromCache = false;

	public LoadWeb(String link, WebView webView) {
		this.link = link;
		this.webView = webView;

	}

	@Override
	protected String doInBackground(String... params) {
		if (BasicFunctions.isConnectingToInternet(MainActivity.activity)) {
			try {

				setDoc(org.jsoup.Jsoup.connect(link).get());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(new Exception("not connect"));
			}

			if (doc != null) {
				Element title = doc.body().getElementsByClass("title_news")
						.first();
				if (title != null) { // in normal page

					Element content = doc.body()
							.getElementsByClass("fck_detail").first();
					String contentHTML = "";
					Elements slideshow = doc.body().getElementsByClass(
							"item_slide_show");
					for (Element slide : slideshow) {
						String slideImageHtml = getSlideImage(slide);

						contentHTML += slideImageHtml;

					}
					if (content != null) {
						imageHandle(content);
						tableHandle(content);
						contentHTML += content.html();
					}

					DisplayWebNewsFragment.htmlContent = title.html()
							+ contentHTML;

				} else { // in video page

					title = doc.body().getElementsByClass("video_top_title")
							.first();
					Element aTag = title.getElementsByTag("a").first();
					aTag.removeAttr("href");
					aTag.attr("style", "font-size:250%;font-weight:bold");
					String htmlVideo = getVideo(doc.body()
							.getElementsByAttributeValue("name", "flashvars")
							.first());

					Element short_des = doc.body()
							.getElementsByClass("video_top_lead").first();
					String html = "<div>" + title.html() + "<p>"
							+ short_des.html() + "<p>" + htmlVideo
							+ "<br><br></div>";
					DisplayWebNewsFragment.htmlContent = html;

				}
			}
		}else {
			DisplayWebNewsFragment.htmlContent = "No internet acess";
		}
		return null;
		// TODO Auto-generated method stub

	}

	private void tableHandle(Element content) {
		// TODO Handle table to fit webview
		Elements tables = content.getElementsByTag("table");
		for (Element table : tables) {
			table.attr("style", "width: 100%");
			table.attr("border", "0");

		}
	}

	private void imageHandle(Element content) {
		// TODO Handle image in webpage to load easily
		Elements imgs = content.getElementsByTag("img");

		for (Element img : imgs) {

			Attributes atts = img.attributes();
			String src = atts.get("src");
			for (Attribute att : atts) {
				atts.remove(att.getKey());
			}
			img.attr("src", src);
			img.attr("width", "100%");
		}
	}

	private String getSlideImage(Element slide) {
		/**
		 * get Slide image html in case of the webpage containing slide images
		 */
		Element slideImage = slide.getElementsByTag("img").first();

		String caption = slideImage.attributes().get("data-component-caption");
		Attributes atts = slideImage.attributes();
		String src = atts.get("src");
		for (Attribute att : atts) {

			atts.remove(att.getKey());

		}
		slideImage.attr("src", src);
		slideImage.attr("width", "100%");
		String html = slideImage.toString();
		return html + caption;
	}

	private String getVideo(Element videoTag) {
		// TODO get Video Html in case of the webpage is video page
		String value = videoTag.attributes().get("value");
		String openString = "trackurl=";
		String closeString = "&objectid=";
		int startIndex = value.indexOf(openString);
		int endIndex = value.indexOf(closeString);
		String videoLink = value.substring(startIndex + openString.length(),
				endIndex);
		String htmlVideo = "<video controls=\"\" autoplay=\"\" name=\"media\" width=\"100%\"><source src=\""
				+ videoLink + "\" type=\"video/mp4\" ></video>";

		return htmlVideo;

	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	@Override
	protected void onPostExecute(String args) {
		
		webView.loadData(DisplayWebNewsFragment.htmlContent,
				"text/html; charset=UTF-8", null);

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		

	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

}

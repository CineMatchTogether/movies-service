package com.service.movies.kinopoiskparser;

import com.service.movies.models.events.UserHistoryEvent;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class KinoPoiskParserService {

    private final RestTemplate delayedRestTemplate;
    private final UserHistoryService userHistoryService;
    private static final Logger logger = LoggerFactory.getLogger(KinoPoiskParserService.class);

    public synchronized void fetchAndParseKinoPoiskPage(UUID userId, String kinoPoiskUserId, String cookie) {

        boolean hasNextPage = true;
        int pageNumber = 1;

        while (hasNextPage) {
            try {
                // imitation browser
                HttpHeaders headers = new HttpHeaders();
                headers.set("Cookie", cookie);
                headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
                HttpEntity<String> entity = new HttpEntity<>(headers);
                String url = generateUrl(kinoPoiskUserId, pageNumber);

                ResponseEntity<String> response = delayedRestTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        String.class
                );

                String html = response.getBody();
                var res = parseKinopoiskPage(html);
                userHistoryService.sendUserHistory(userId, res);
                logger.info(res.toString());

                hasNextPage = res.hasNextPage();
                pageNumber++;

            } catch (Exception e) {
                logger.error("Error fetching page: " + e.getMessage());
                userHistoryService.sendUserHistory(userId, new UserHistoryEvent("Error fetching page", new ArrayList<>(), false));
                return;
            }
        }
    }

    private UserHistoryEvent parseKinopoiskPage(String html) {
        Document doc = Jsoup.parse(html);
        String pagesInfo = extractPagesInfo(doc);
        List<Long> filmIds = extractFilmIds(doc);
        boolean hasNextPage = extractHasNextPage(doc);
        return new UserHistoryEvent(pagesInfo, filmIds, hasNextPage);
    }

    private String extractPagesInfo(Document doc) {
        Element pagesElement = doc.selectFirst("div.pagesFromTo");
        return pagesElement != null ? pagesElement.text() : "Not found";
    }

    private List<Long> extractFilmIds(Document doc) {
        List<Long> filmIds = new ArrayList<>();
        Elements items = doc.select("div.item");
        Pattern pattern = Pattern.compile("/film/(\\d+)/");

        for (Element item : items) {
            Element link = item.selectFirst("div.nameRus a[href]");
            if (link != null) {
                String href = link.attr("href");
                Matcher matcher = pattern.matcher(href);
                if (matcher.find()) {
                    filmIds.add(Long.valueOf(matcher.group(1)));
                }
            }
        }
        return filmIds;
    }

    public boolean extractHasNextPage(Document doc) {
        Elements nextPageElements = doc.select("li.arr a:contains(»)");

        return !nextPageElements.isEmpty();
    }

    private String generateUrl(String userId, int page) {
        return String.format("https://www.kinopoisk.ru/user/%s/votes/list/vs/novote/perpage/200/page/%d/#list", userId, page);
    }
}

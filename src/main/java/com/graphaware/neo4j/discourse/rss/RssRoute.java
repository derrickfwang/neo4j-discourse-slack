package com.graphaware.neo4j.discourse.rss;

import com.graphaware.neo4j.discourse.filter.TagFilter;
import com.graphaware.neo4j.discourse.notification.SlackNotifier;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RssRoute extends RouteBuilder {

    @Autowired
    private RssItemProcessor rssItemProcessor;

    @Autowired
    private SlackNotifier slackNotifier;

    @Autowired
    private TagFilter tagFilter;

    @Override
    public void configure() throws Exception {
        from("rss:https://community.neo4j.com/latest.rss")
                .process(rssItemProcessor)
                .filter().method(tagFilter, "filterByTag")
                .process(slackNotifier);
    }
}

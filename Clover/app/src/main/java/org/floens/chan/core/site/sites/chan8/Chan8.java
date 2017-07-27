/*
 * Clover - 4chan browser https://github.com/Floens/Clover/
 * Copyright (C) 2014  Floens
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.floens.chan.core.site.sites.chan8;


import android.support.annotation.Nullable;
import android.webkit.WebView;

import org.floens.chan.chan.ChanLoaderRequest;
import org.floens.chan.chan.ChanLoaderRequestParams;
import org.floens.chan.core.model.Post;
import org.floens.chan.core.model.orm.Board;
import org.floens.chan.core.model.orm.Loadable;
import org.floens.chan.core.site.Resolvable;
import org.floens.chan.core.site.Site;
import org.floens.chan.core.site.SiteAuthentication;
import org.floens.chan.core.site.SiteBase;
import org.floens.chan.core.site.SiteEndpoints;
import org.floens.chan.core.site.SiteIcon;
import org.floens.chan.core.site.SiteRequestModifier;
import org.floens.chan.core.site.common.ChanReaderRequest;
import org.floens.chan.core.site.http.DeleteRequest;
import org.floens.chan.core.site.http.HttpCall;
import org.floens.chan.core.site.http.LoginRequest;
import org.floens.chan.core.site.http.Reply;

import java.util.Locale;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class Chan8 extends SiteBase {
    public static final Resolvable RESOLVABLE = new Resolvable() {
        @Override
        public ResolveResult resolve(String value) {
            if (value.equals("8chan")) {
                return ResolveResult.NAME_MATCH;
            } else if (value.equals("https://8ch.net/")) {
                return ResolveResult.FULL_MATCH;
            } else {
                return ResolveResult.NO;
            }
        }

        @Override
        public Class<? extends Site> getSiteClass() {
            return Chan8.class;
        }
    };

    private final SiteEndpoints endpoints = new SiteEndpoints() {
        private final HttpUrl root = new HttpUrl.Builder()
                .scheme("https")
                .host("8ch.net")
                .build();

        private final HttpUrl media = new HttpUrl.Builder()
                .scheme("https")
                .host("media.8ch.net")
                .build();

        @Override
        public HttpUrl catalog(Board board) {
            return root.newBuilder()
                    .addPathSegment(board.code)
                    .addPathSegment("catalog.json")
                    .build();
        }

        @Override
        public HttpUrl thread(Board board, Loadable loadable) {
            return root.newBuilder()
                    .addPathSegment(board.code)
                    .addPathSegment("res")
                    .addPathSegment(loadable.no + ".json")
                    .build();
        }

        @Override
        public HttpUrl imageUrl(Post.Builder post, Map<String, String> arg) {
            return root.newBuilder()
                    .addPathSegment("file_store")
                    .addPathSegment(arg.get("tim") + "." + arg.get("ext"))
                    .build();
        }

        @Override
        public HttpUrl thumbnailUrl(Post.Builder post, boolean spoiler, Map<String, String> arg) {
            return root.newBuilder()
                    .addPathSegment("file_store")
                    .addPathSegment("thumb")
                    .addPathSegment(arg.get("tim") + "." + arg.get("ext"))
                    .build();
        }

        @Override
        public HttpUrl icon(Post.Builder post, String icon, Map<String, String> arg) {
            HttpUrl.Builder stat = root.newBuilder().addPathSegment("static");

            switch (icon) {
                case "country":
                    stat.addPathSegment("flags");
                    stat.addPathSegment(arg.get("country_code").toLowerCase(Locale.ENGLISH) + ".png");
                    break;
            }

            return stat.build();
        }

        @Override
        public HttpUrl boards() {
            return null;
        }

        @Override
        public HttpUrl reply(Loadable loadable) {
            return null;
        }

        @Override
        public HttpUrl delete(Post post) {
            return null;
        }

        @Override
        public HttpUrl report(Post post) {
            return null;
        }

        @Override
        public HttpUrl login() {
            return null;
        }
    };

    private SiteRequestModifier siteRequestModifier = new SiteRequestModifier() {
        @Override
        public void modifyHttpCall(HttpCall httpCall, Request.Builder requestBuilder) {
        }

        @SuppressWarnings("deprecation")
        @Override
        public void modifyWebView(WebView webView) {
        }
    };

    private SiteAuthentication authentication = new SiteAuthentication() {
        @Override
        public boolean requireAuthentication(AuthenticationRequestType type) {
            return false;
        }
    };

    @Override
    public String name() {
        return "8chan";
    }

    @Override
    public SiteIcon icon() {
        return SiteIcon.fromAssets("icons/8chan.png");
    }

    @Override
    public boolean feature(Feature feature) {
        return false;
    }

    @Override
    public boolean boardFeature(BoardFeature boardFeature, Board board) {
        return false;
    }

    @Override
    public SiteEndpoints endpoints() {
        return endpoints;
    }

    @Override
    public SiteRequestModifier requestModifier() {
        return siteRequestModifier;
    }

    @Override
    public SiteAuthentication authentication() {
        return authentication;
    }

    @Override
    public BoardsType boardsType() {
        return BoardsType.INFINITE;
    }

    @Override
    public String desktopUrl(Loadable loadable, @Nullable Post post) {
        return "https://8ch.net/";
    }

    @Override
    public void boards(BoardsListener boardsListener) {
    }

    @Override
    public Board board(String code) {
        return null;
    }

    @Override
    public ChanLoaderRequest loaderRequest(ChanLoaderRequestParams request) {
        return new ChanLoaderRequest(new ChanReaderRequest(request));
    }

    @Override
    public void post(Reply reply, PostListener postListener) {
    }

    @Override
    public void delete(DeleteRequest deleteRequest, DeleteListener deleteListener) {
    }

    @Override
    public void login(LoginRequest loginRequest, LoginListener loginListener) {
    }

    @Override
    public void logout() {
    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public LoginRequest getLoginDetails() {
        return null;
    }
}

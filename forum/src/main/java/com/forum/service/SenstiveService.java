package com.forum.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class SenstiveService implements InitializingBean{

    public TrieNode rootNode = new TrieNode();//根节点
    private static final String DEFAULT_REPLACEMENT = "**";//敏感词替换
    public String filter(String text){
        String replacement = DEFAULT_REPLACEMENT;
        int position = 0;
        int begin = 0;
        StringBuilder builder = new StringBuilder();
        TrieNode tempNode = rootNode;
        while (position < text.length()){
            char c = text.charAt(position);
            if (isSymbol(c)){
                if (tempNode == rootNode){
                    builder.append(c);
                    ++ begin;
                }
                ++ position;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null){
                builder.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                tempNode = rootNode;
            }else if (tempNode.isKeywordEnd()){
                builder.append(replacement);
                position = position + 1;
                begin = position;
                tempNode = rootNode;
            }else {
                position ++;
            }
        }
        builder.append(text.substring(begin));
        return builder.toString();
    }

    public void addKeyword(String word){
        if (StringUtils.isBlank(word)){
            return;
        }
        TrieNode tempNode = rootNode;//临时节点
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (isSymbol(c)){
                continue;
            }
            TrieNode node = tempNode.getSubNode(c);
            if (node == null){
                node = new TrieNode();
                tempNode.addSubNode(c,node);
            }
            tempNode = node;
            if (i == (word.length() - 1)){
                tempNode.setKeywordEnd(true);
            }
        }
    }

    private boolean isSymbol(char c){
        int ic = (int)c;
        //东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    public static void main(String[] args) {
        SenstiveService senstiveService = new SenstiveService();
        senstiveService.addKeyword("好人");
        senstiveService.addKeyword("坏人");
        System.out.println(senstiveService.filter("我是一个好***人，他是一个坏 人"));
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    private class TrieNode{

        private boolean end = false;

        private Map<Character,TrieNode> subNodes = new HashMap<>();

        public void setKeywordEnd(boolean end){
            this.end = end;
        }
        public boolean isKeywordEnd(){
            return this.end;
        }
        public void addSubNode(Character key,TrieNode value){
            this.subNodes.put(key,value);
        }
        public TrieNode getSubNode(Character key){
            return this.subNodes.get(key);
        }
    }
}

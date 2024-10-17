package pl.chudziudgi.paymc.configuration;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

public class PluginConfiguration extends OkaeriConfig {
    public List<String> allowCommandsInAntiLogout = List.of("msg");
    public double knock = 0.05;

    public ChatSettings chatSettings = new ChatSettings();


    public static class ChatSettings extends OkaeriConfig {
        public boolean chatStatus = true;
        public String chatFormat = "%luckperms_prefix% &7{PLAYER}&8: &f";
        public List<String> listaDozwolonychZnakow = Arrays.asList(" ", "A", "Ą", "B", "C", "Ć", "D", "E", "Ę", "F", "G", "H", "I", "J", "K", "L", "Ł", "M", "N", "Ń", "O", "Ó", "P", "Q", "R", "S", "Ś", "T", "U", "V", "W", "X", "Y", "Z", "Ź", "Ż", "a", "ą", "b", "c", "ć", "d", "e", "ę", "f", "g", "h", "i", "j", "k", "l", "ł", "m", "n", "ń", "o", "ó", "p", "q", "r", "s", "ś", "t", "u", "v", "w", "x", "y", "z", "ź", "ż", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "!", "?", ".", ",", ";", ":", "-", "_", "(", ")", "[", "]", "{", "}", "<", ">", "'", "\"", "@", "#", "$", "^", "&", "*", "+", "=", "/", "\\", "|", "~", "`");

        public boolean isChatStatus() {
            return this.chatStatus;
        }

        public void setChatStatus(boolean chatStatus) {
            this.chatStatus = chatStatus;
        }

        public String getChatFormat() {
            return this.chatFormat;
        }

        public void setChatFormat(String chatFormat) {
            this.chatFormat = chatFormat;
        }

        public List<String> getListaDozwolonychZnakow() {
            return this.listaDozwolonychZnakow;
        }

        public void setListaDozwolonychZnakow(List<String> listaDozwolonychZnakow) {
            this.listaDozwolonychZnakow = listaDozwolonychZnakow;
        }
    }
}
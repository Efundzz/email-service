package com.efundzz.emailservice.service;

import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

@Service
public class MXLookupService {

    public String getMXRecord(String hostName) throws NamingException {

        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");

        DirContext dirContext = new InitialDirContext(env);

        Attributes attrs = dirContext.getAttributes(hostName, new String[]{"MX"});
        return attrs.toString();
    }
}

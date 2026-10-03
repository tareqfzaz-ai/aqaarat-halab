const CACHE="aqaarat-halab-v1";
const APP_SHELL=["./","./index.html","./manifest.webmanifest","./icon.svg","./cover.png"];
self.addEventListener("install",e=>e.waitUntil(caches.open(CACHE).then(c=>c.addAll(APP_SHELL)).then(()=>self.skipWaiting())));
self.addEventListener("activate",e=>e.waitUntil(self.clients.claim()));
self.addEventListener("fetch",e=>{
  if(e.request.method!=="GET") return;
  e.respondWith(fetch(e.request).then(r=>{
    const copy=r.clone();
    caches.open(CACHE).then(c=>c.put(e.request,copy)).catch(()=>{});
    return r;
  }).catch(()=>caches.match(e.request).then(r=>r||caches.match("./index.html"))));
});
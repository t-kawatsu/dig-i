<div id="tweets" style="height:320px"></div>
<script>
$(document).ready(function() {
new TWTR.Widget({
  id: "tweets",
  version: 2,
  type: 'search',
  search: '${tag.name?html}',
  interval: 2000,
  title: '${tag.name?html}',
  height: 300,
  theme: {
    shell: {
      background: '#ffffff',
      color: '#ffffff'
    },
    tweets: {
      background: '#ffffff',
      color: '#222',
      links: '#1985b5'
    }
  },
  features: {
    scrollbar: false,
    loop: false,
    live: true,
    behavior: 'default'
  }
}).render().start();
});
</script>

package pl.lotto.numberreceiver;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryTicketRepository implements TicketRepository {
    private final ConcurrentHashMap<String, Ticket> map = new ConcurrentHashMap<>();

    @Override
    public Ticket save(Ticket ticket) {
        map.put(ticket.getHash(), ticket.dto());
        return ticket;
    }

    @Override
    public Optional<Ticket> findByHash(String hash) {
        Ticket ticket = map.get(hash);
        return Optional.of(ticket)
                .map(Ticket::dto);
    }

    @Override
    public Optional<Ticket> findByDate(String dateDraw){
        Ticket ticket = map.get(dateDraw);
        return Optional.of(ticket)
                .map(Ticket::dto);
    }
}

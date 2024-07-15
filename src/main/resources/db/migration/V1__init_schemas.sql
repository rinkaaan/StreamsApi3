CREATE TABLE stream (
    id UUID PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE recording (
    id UUID PRIMARY KEY,
    url VARCHAR NOT NULL,
    stream_id UUID,
    CONSTRAINT fk_stream
      FOREIGN KEY(stream_id)
      REFERENCES stream(id)
);

CREATE TABLE schedule (
    id UUID PRIMARY KEY,
    url VARCHAR NOT NULL,
    stream_id UUID,
    CONSTRAINT fk_stream
      FOREIGN KEY(stream_id)
      REFERENCES stream(id)
);

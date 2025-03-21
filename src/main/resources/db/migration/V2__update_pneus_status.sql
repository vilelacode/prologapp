UPDATE pneu SET status = 'EM_USO'
WHERE numero_fogo IN (
                      'FOGO001', 'FOGO002', 'FOGO003', 'FOGO004', 'FOGO005', 'FOGO006',
                      'FOGO028', 'FOGO029', 'FOGO030', 'FOGO031'
    );
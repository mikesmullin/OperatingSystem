const root = document.body;

var Hello = {
    oncreate: v => {
        $('input', v.dom)
            .on('focus', function() { this.select(); })
            .on('keypress', e => {
                switch (e.key) {
                    case "0":
                    case "1":
                        setTimeout(()=>{ $(e.target).next('input').focus(); }, 0);
                        break;
                    default:
                        e.preventDefault();
                        break;
                }
            });
    },
    view: function() {
        const input = () => m('input', { maxlength: 1, value: 0 });
        const group = ({size, color, label, description, children}) => m(`.group.bg-${color}`, [
            m('.description', [
                m('label', `${label}:`),
                ` ${description}`
            ]),
        ].concat(children ? children : new Array(size).fill(0).map(v=>
            input()
        )));

        return m('.container-fluid', [
            m('.row.mt-2', [
                m('.col-12',
                    m("h1", "ADD ― Add"),
                ),
            ]),
            m('.row', [
                m('.col-12',
                    m('.instruction',
                        group({ size: 4*8, label: 'Prefix',
                            description: '0, 1, 2, or 4 bytes of performance modifiers. (ie., repeat until, lock memory, bind cpu, branch hint, size overrides)' }),
                        group({ size: 3*8, label: 'Opcode',
                            description: '1, 2, or 3 byte instruction identifier.' }),
                        group({ label: 'Mod-R/M',
                            description: 'One byte addressing mode (register or memory) and operand size.',
                            children: [
                                group({ size: 2, label: 'Mod',
                                  description: 'Specifies x86 addressing mode.' }),
                                group({ size: 3, label: 'Reg',
                                    description: 'Specifies source or destination register.' }),
                                group({ size: 3, label: 'R/M',
                                 description: `Combined with Mod, specifies either
                                    the second operand in a two-operand instruction, or
                                    the only operand in a single-operand instruction.` }),
                            ]}),
                        group({ label: 'SIB (Scaled Index Byte)',
                            description: 'One byte scaled index memory address.',
                            children: [
                                group({ size: 2, label: 'Scale',
                                  description: '1, 2, 4, or 8 factor.' }),
                                group({ size: 3, label: 'Index',
                                    description: 'Index register.' }),
                                group({ size: 3, label: 'Base',
                                 description: `Base register.` }),
                            ]}),
                        group({ size: 4*8, label: 'Displacement',
                            description: '0 - 32 bit address offset.' }),
                        group({ size: 4*8, label: 'Immediate',
                            description: '0 - 32 bit constant.' }),
                    ),
                ),
            ]),
        ]);
    }
}

m.mount(root, Hello)
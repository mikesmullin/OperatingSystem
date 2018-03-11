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
        $('.description .tooltip', v.dom).hide().map((i,e) =>
            $(e).prev('label').on('click', ()=>$(e).toggle()));
        $('.group a.remove', v.dom).on('click', function() { $(this).closest('.b-g').slideUp({ duration: 250, easing: 'linear', complete: function(){ $(this).remove(); }}); });
    },
    view: function() {
        const repeat = (count, e) => new Array(count).fill(0).map(e);
        const mIf = (t, r) => t ? [r] : [];
        const inputs = (id, count, remove) => {
            return m(`.b-g.b-g-${id}`, [
                repeat(count, (nil, i) =>
                    m(`input.b.b-${i}`, { maxlength: 1, value: 0 })),                
            ].concat(mIf(remove,
                m('a.remove', { href: '#' }, m('i.fas.fa-fw.fa-minus-square.red.ml-1'))
            )));
        }
        const group = ({label, description, children}) => m(`.group`, [
                m('.description', [
                    m('label', [
                        `${label}: `,
                        m('i.blue.fas.fa-info-circle.fa-sm')
                    ]),
                    m('.tooltip', `${description}`),
                ]),
            ].concat(children));

        return m('.container-fluid', [
            m('.row.mt-2', [
                m('.col-12',
                    m("h1", "ADD ― Add"),
                ),
            ]),
            m('.row', [
                m('.col-12',
                    m('.instruction',
                        group({ label: 'Prefix',
                            description: '0, 1, 2, or 4 bytes of performance modifiers. (ie., repeat until, lock memory, bind cpu, branch hint, size overrides)',
                            children: [
                                inputs(1, 8, true),
                                inputs(2, 8, true),
                                inputs(3, 8, true),
                                inputs(4, 8, true),
                            ]}),
                        group({ label: 'Opcode',
                            description: '1, 2, or 3 byte instruction identifier.',
                            children: [
                                inputs(1, 8, false),
                                inputs(2, 8, true),
                                inputs(3, 8, true),
                            ]}),
                        group({ label: 'Mod-R/M',
                            description: 'One byte addressing mode (register or memory) and operand size.',
                            children: [
                                group({ label: 'Mod',
                                  description: 'Specifies x86 addressing mode.',
                                  children: [
                                      inputs(1, 2, false),
                                  ]}),
                                group({ label: 'Reg',
                                    description: 'Specifies source or destination register.',
                                    children: [
                                        inputs(1, 3, false),
                                    ]}),
                                group({ label: 'R/M',
                                 description: `Combined with Mod, specifies either
                                    the second operand in a two-operand instruction, or
                                    the only operand in a single-operand instruction.`,
                                    children: [
                                        inputs(1, 3, false),
                                    ]}),
                            ]}),
                        group({ label: 'SIB (Scaled Index Byte)',
                            description: 'One byte scaled index memory address.',
                            children: [
                                group({ label: 'Scale',
                                  description: '1, 2, 4, or 8 factor.',
                                  children: [
                                      inputs(1, 2, false),
                                  ]}),
                                group({ label: 'Index',
                                    description: 'Index register.',
                                    children: [
                                        inputs(1, 3, false),
                                    ]}),
                                group({ label: 'Base',
                                    description: `Base register.`,
                                    children: [
                                        inputs(1, 3, false),
                                    ]}),
                            ]}),
                        group({ label: 'Displacement',
                            description: '0 - 32 bit address offset.',
                            children: [
                                inputs(1, 8, false),
                                inputs(1, 8, false),
                                inputs(1, 8, false),
                                inputs(1, 8, false),
                            ]}),
                        group({ label: 'Immediate',
                            description: '0 - 32 bit constant.',
                            children: [
                                inputs(1, 8, false),
                                inputs(1, 8, false),
                                inputs(1, 8, false),
                                inputs(1, 8, false),
                            ]}),
                    ),
                ),
            ]),
        ]);
    }
}

m.mount(root, Hello)